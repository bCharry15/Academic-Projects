# Compatible con Windows PowerShell 5.1. No requiere modulos adicionales.
# Se puede importar para pruebas: . .\herramientas\Configurar-Entradas.ps1
[CmdletBinding()]
param()

Set-StrictMode -Version 2.0
$ErrorActionPreference = 'Stop'
$script:FpgaRoot = Split-Path -Parent $PSScriptRoot
$script:OperationNames = @('NOT A', 'A AND B', 'Transferir A', 'A OR B', 'A - 1', 'A + B', 'A - B', 'A + 1')

function ConvertTo-ByteOperand {
    [CmdletBinding()]
    param([Parameter(Mandatory = $true)][AllowEmptyString()][string]$Text)
    $valueText = $Text.Trim()
    $number = 0
    if ($valueText -match '^0[xX]([0-9a-fA-F]{1,2})$') {
        $number = [Convert]::ToInt32($Matches[1], 16)
    }
    elseif ($valueText -match '^[0-9]{1,3}$') {
        $number = [int]::Parse($valueText, [Globalization.CultureInfo]::InvariantCulture)
    }
    else {
        throw 'Use un entero decimal de 0 a 255, o hexadecimal con prefijo 0x (ejemplo: 0xFF).'
    }
    if ($number -gt 255) { throw 'El valor debe estar entre 0 y 255 (0x00 a 0xFF).' }
    return $number
}

function New-EightOperationWords {
    [CmdletBinding()]
    param(
        [Parameter(Mandatory = $true)][ValidateRange(0, 255)][int]$AndB,
        [Parameter(Mandatory = $true)][ValidateRange(0, 255)][int]$OrB,
        [Parameter(Mandatory = $true)][ValidateRange(0, 255)][int]$SumB,
        [Parameter(Mandatory = $true)][ValidateRange(0, 255)][int]$SubB
    )
    # El PC asciende: las direcciones 0..7 contienen las operaciones 0..7.
    return @(0x000, (0x100 + $AndB), 0x200, (0x300 + $OrB), 0x400, (0x500 + $SumB), (0x600 + $SubB), 0x700)
}

function New-IndependentOperationWords {
    [CmdletBinding()]
    param(
        [Parameter(Mandatory = $true)][ValidateRange(0, 255)][int]$A,
        [Parameter(Mandatory = $true)][ValidateRange(0, 7)][int]$Operation,
        [ValidateRange(0, 255)][int]$B = 0
    )
    if ($Operation -notin @(1, 3, 5, 6)) { $B = 0 }
    return @((0x300 + $A), (($Operation -shl 8) + $B), 0x200, 0x200, 0x200, 0x200, 0x200, 0x200)
}

function Assert-ProgramWords {
    param([Parameter(Mandatory = $true)][int[]]$Words)
    if ($Words.Count -ne 8) { throw 'La ROM debe contener exactamente ocho palabras.' }
    foreach ($word in $Words) {
        if ($word -lt 0 -or $word -gt 0x7FF) { throw 'Cada palabra de la ROM debe tener 11 bits (000 a 7FF).' }
    }
}

function Get-ProgramWords {
    [CmdletBinding()]
    param([string]$Root = $script:FpgaRoot, [string]$Path)
    if (-not $Path) { $Path = Join-Path $Root 'PROYECTO_INTEGRADO\miROM.mif' }
    $content = [IO.File]::ReadAllText($Path)
    $content = [regex]::Replace($content, '(?s)%.*?%', '')
    $content = [regex]::Replace($content, '(?m)--.*$', '')
    foreach ($required in @('(?i)\bWIDTH\s*=\s*11\s*;', '(?i)\bDEPTH\s*=\s*8\s*;', '(?i)\bADDRESS_RADIX\s*=\s*UNS\s*;', '(?i)\bDATA_RADIX\s*=\s*HEX\s*;')) {
        if ($content -notmatch $required) { throw 'Formato MIF inesperado: se requiere WIDTH=11, DEPTH=8, ADDRESS_RADIX=UNS y DATA_RADIX=HEX.' }
    }
    $block = [regex]::Match($content, '(?is)\bCONTENT\s+BEGIN\s*(.*?)\bEND\s*;')
    if (-not $block.Success) { throw 'No se encontro el bloque CONTENT BEGIN ... END del MIF.' }
    $body = $block.Groups[1].Value
    $entryPattern = '(?i)(\d+)\s*:\s*([0-9a-f]{1,3})\s*;'
    if ([regex]::Replace($body, $entryPattern, '').Trim().Length -ne 0) {
        throw 'El MIF debe contener una asignacion por direccion, de 0 a 7; no se admiten rangos en este configurador.'
    }
    $entries = [regex]::Matches($body, $entryPattern)
    $words = New-Object 'int[]' 8
    $seen = @{}
    foreach ($entry in $entries) {
        $address = [int]$entry.Groups[1].Value
        if ($address -lt 0 -or $address -gt 7 -or $seen.ContainsKey($address)) {
            throw 'Direcciones invalidas o duplicadas en el MIF; deben aparecer una vez cada una de 0 a 7.'
        }
        $seen[$address] = $true
        $words[$address] = [Convert]::ToInt32($entry.Groups[2].Value, 16)
    }
    if ($seen.Count -ne 8) { throw 'Faltan direcciones del MIF; deben aparecer todas de 0 a 7.' }
    Assert-ProgramWords -Words $words
    return $words
}

function Get-ProgramTrace {
    [CmdletBinding()]
    param([Parameter(Mandatory = $true)][int[]]$Words)
    Assert-ProgramWords -Words $Words
    $acc = 0
    $step = 0
    foreach ($address in 0..7) {
        $step++
        $word = $Words[$address]
        $opcode = $word -shr 8
        $b = $word -band 255
        $a = $acc
        switch ($opcode) {
            0 { $acc = $a -bxor 255 }
            1 { $acc = $a -band $b }
            2 { $acc = $a }
            3 { $acc = $a -bor $b }
            4 { $acc = ($a - 1) -band 255 }
            5 { $acc = ($a + $b) -band 255 }
            6 { $acc = ($a - $b) -band 255 }
            7 { $acc = ($a + 1) -band 255 }
        }
        [pscustomobject]@{
            Step = $step; Address = $address; Word = ('{0:X3}' -f $word)
            Operation = $script:OperationNames[$opcode]; A = $a; B = $b
            UsesB = ($opcode -in @(1, 3, 5, 6)); Result = $acc; Hex = ('{0:X2}' -f $acc)
        }
    }
}

function Get-CurrentConfigurationText {
    [CmdletBinding()]
    param([string]$Root = $script:FpgaRoot)
    $words = @(Get-ProgramWords -Root $Root)
    $trace = @(Get-ProgramTrace -Words $words)
    $lines = New-Object 'System.Collections.Generic.List[string]'
    $lines.Add('CONFIGURACION ACTUAL DEL PROYECTO FPGA')
    $lines.Add('Generada leyendo PROYECTO_INTEGRADO\miROM.mif el ' + (Get-Date -Format 'yyyy-MM-dd HH:mm:ss'))
    $lines.Add('')
    $lines.Add('Los valores de abajo son resultados esperados del modelo de ocho bits.')
    $lines.Add('Compruebelos ejecutando una simulacion nueva en Quartus desde reset.')
    $lines.Add('Si edita el MIF manualmente, vuelva al menu 4 para actualizar este informe.')
    $lines.Add('')
    $lines.Add('ROM activa (direccion : palabra hexadecimal):')
    for ($i = 0; $i -lt 8; $i++) { $lines.Add(('  {0} : {1:X3};' -f $i, $words[$i])) }
    $lines.Add('')
    $lines.Add('Ejecucion desde Reset: ACC = 00 hexadecimal (0 decimal).')
    $lines.Add('Paso Dir ROM Operacion     A(dec) B(dec) ACC(hex) ACC(dec)')
    foreach ($row in $trace) {
        $bText = if ($row.UsesB) { [string]$row.B } else { '-' }
        $lines.Add(('{0,4} {1,3} {2,3} {3,-12} {4,6} {5,6} {6,8} {7,8}' -f $row.Step, $row.Address, $row.Word, $row.Operation, $row.A, $bText, $row.Hex, $row.Result))
    }
    $lines.Add('')
    $lines.Add('ACC hexadecimal: 00 -> ' + (($trace | ForEach-Object { $_.Hex }) -join ' -> '))
    $lines.Add('Se conservan los ocho bits inferiores: 255+1=0; 0-1=255.')
    $lines.Add('En las ocho operaciones, A es el resultado acumulado del paso anterior.')
    $lines.Add('En una prueba A/B independiente, el paso 1 carga A, el paso 2 opera y los demas conservan el resultado.')
    $lines.Add('')
    $lines.Add('PARA APLICAR LOS CAMBIOS Y SIMULAR:')
    $lines.Add('1. Si miROM.mif esta abierto en un editor, recarguelo; no guarde una version vieja sobre la nueva.')
    $lines.Add('2. Abra PROYECTO_INTEGRADO\PROYECTO_TOP.qpf de esta misma carpeta en Quartus.')
    $lines.Add('3. Ejecute Processing > Start Compilation y espere a que termine sin errores.')
    $lines.Add('4. Abra PROYECTO_INTEGRADO\SIMULACION_COMPLETA.vwf.')
    $lines.Add('5. Ejecute Simulation > Run Functional Simulation desde el inicio, con el reset incluido.')
    $lines.Add('6. Muestre ACC y ROM_OUT en hexadecimal. Observe ACC despues de cada flanco ascendente de PULSO.')
    $lines.Add('No basta con abrir resultados anteriores: es necesario volver a compilar y simular.')
    $lines.Add('Si movio el proyecto y el editor conserva una ruta vieja, seleccione el VWF actual en Simulation Settings.')
    $lines.Add('')
    $lines.Add('Para volver al programa original, use la opcion 3 del configurador y compile/simule de nuevo.')
    $lines.Add('Reset reinicia la ejecucion; no restaura la ROM original.')
    return ($lines -join [Environment]::NewLine) + [Environment]::NewLine
}

function Update-CurrentConfiguration {
    [CmdletBinding()]
    param([string]$Root = $script:FpgaRoot)
    $text = Get-CurrentConfigurationText -Root $Root
    [IO.File]::WriteAllText((Join-Path $Root 'PROYECTO_INTEGRADO\CONFIGURACION_ACTUAL.txt'), $text, (New-Object Text.UTF8Encoding($true)))
    return $text
}

function Set-ProgramWords {
    [CmdletBinding()]
    param([Parameter(Mandatory = $true)][int[]]$Words, [string]$Root = $script:FpgaRoot)
    Assert-ProgramWords -Words $Words
    $lines = @('WIDTH=11;', 'DEPTH=8;', 'ADDRESS_RADIX=UNS;', 'DATA_RADIX=HEX;', 'CONTENT BEGIN')
    for ($i = 0; $i -lt 8; $i++) { $lines += ('    {0} : {1:X3};' -f $i, $Words[$i]) }
    $lines += 'END;'
    $destination = Join-Path $Root 'PROYECTO_INTEGRADO\miROM.mif'
    $temporary = $destination + '.' + [Guid]::NewGuid().ToString('N') + '.tmp'
    try {
        [IO.File]::WriteAllText($temporary, (($lines -join "`r`n") + "`r`n"), [Text.Encoding]::ASCII)
        if ([IO.File]::Exists($destination)) { [IO.File]::Replace($temporary, $destination, [System.Management.Automation.Language.NullString]::Value) }
        else { [IO.File]::Move($temporary, $destination) }
    }
    finally { if ([IO.File]::Exists($temporary)) { [IO.File]::Delete($temporary) } }
    return Update-CurrentConfiguration -Root $Root
}

function Restore-OriginalProgram {
    [CmdletBinding()]
    param([string]$Root = $script:FpgaRoot)
    $source = Join-Path $Root 'PROYECTO_INTEGRADO\respaldo_original\miROM.mif'
    # Primero valida; despues copia los bytes originales sin reserializarlos.
    $null = Get-ProgramWords -Path $source
    $original = [IO.File]::ReadAllBytes($source)
    $destination = Join-Path $Root 'PROYECTO_INTEGRADO\miROM.mif'
    $temporary = $destination + '.' + [Guid]::NewGuid().ToString('N') + '.tmp'
    try {
        [IO.File]::WriteAllBytes($temporary, $original)
        if ([IO.File]::Exists($destination)) { [IO.File]::Replace($temporary, $destination, [System.Management.Automation.Language.NullString]::Value) }
        else { [IO.File]::Move($temporary, $destination) }
    }
    finally { if ([IO.File]::Exists($temporary)) { [IO.File]::Delete($temporary) } }
    return Update-CurrentConfiguration -Root $Root
}

function Read-Operand {
    param([string]$Label)
    while ($true) {
        $inputText = Read-Host ($Label + ' [decimal 0..255 o 0x00..0xFF; C cancela]')
        if ($null -eq $inputText -or $inputText.Trim() -ieq 'C') { return $null }
        try { return ConvertTo-ByteOperand -Text $inputText }
        catch { Write-Host $_.Exception.Message -ForegroundColor Yellow }
    }
}

function Show-ProgramPreview {
    param([int[]]$Words)
    Write-Host ''
    Write-Host 'ACC esperado despues de cada pulsacion (hexadecimal):'
    $trace = @(Get-ProgramTrace -Words $Words)
    Write-Host ('00 -> ' + (($trace | ForEach-Object { $_.Hex }) -join ' -> ')) -ForegroundColor Cyan
}

function Start-FpgaMenu {
    [CmdletBinding()]
    param([string]$Root = $script:FpgaRoot)
    Write-Host 'CONFIGURADOR DE ENTRADAS FPGA' -ForegroundColor Cyan
    Write-Host ('Proyecto: ' + (Join-Path $Root 'PROYECTO_INTEGRADO'))
    Write-Host 'Cierre o recargue la pestana miROM.mif en Quartus para evitar guardar datos viejos.'
    while ($true) {
        Write-Host ''
        Write-Host '1. Cambiar B en las ocho operaciones originales'
        Write-Host '2. Probar una operacion con A y B independientes'
        Write-Host '3. Restaurar el programa original'
        Write-Host '4. Ver programa activo y resultados esperados'
        Write-Host '0. Salir'
        $choice = Read-Host 'Opcion'
        if ($null -eq $choice) { return }
        $choice = $choice.Trim()
        try {
            switch ($choice) {
                '0' { return }
                '1' {
                    Write-Host 'Se recupera la secuencia de las ocho operaciones y se reemplazan sus cuatro B.'
                    Write-Host 'A es el acumulador anterior; NOT, transferencia, -1 y +1 no usan B.'
                    $andB = Read-Operand 'B de AND'
                    if ($null -eq $andB) { Write-Host 'Cancelado. No se modifico la ROM.'; break }
                    $orB = Read-Operand 'B de OR'
                    if ($null -eq $orB) { Write-Host 'Cancelado. No se modifico la ROM.'; break }
                    $sumB = Read-Operand 'B de SUMA'
                    if ($null -eq $sumB) { Write-Host 'Cancelado. No se modifico la ROM.'; break }
                    $subB = Read-Operand 'B de RESTA'
                    if ($null -eq $subB) { Write-Host 'Cancelado. No se modifico la ROM.'; break }
                    $words = @(New-EightOperationWords -AndB $andB -OrB $orB -SumB $sumB -SubB $subB)
                    Show-ProgramPreview -Words $words
                    $confirm = Read-Host 'ENTER guarda estos valores; cualquier texto cancela'
                    if ($confirm -ne '') { Write-Host 'Cancelado. No se modifico la ROM.'; break }
                    $null = Set-ProgramWords -Words $words -Root $Root
                    Write-Host 'Guardado. Compile y ejecute una simulacion nueva desde reset.' -ForegroundColor Green
                    Write-Host 'Consulte PROYECTO_INTEGRADO\CONFIGURACION_ACTUAL.txt para ver los resultados y pasos.'
                }
                '2' {
                    Write-Host 'Paso 1: carga A con 0 OR A. Paso 2: operacion elegida. Pasos 3..8: conservan el resultado.'
                    Write-Host 'Empiece la simulacion desde reset para que la carga de A sea correcta.'
                    $a = Read-Operand 'Valor de A'
                    if ($null -eq $a) { Write-Host 'Cancelado. No se modifico la ROM.'; break }
                    for ($i = 0; $i -lt 8; $i++) { Write-Host ('  {0}: {1}' -f $i, $script:OperationNames[$i]) }
                    $operation = $null
                    while ($null -eq $operation) {
                        $opText = Read-Host 'Operacion 0..7 (SUMA o A+B tambien sirven; C cancela)'
                        if ($null -eq $opText -or $opText.Trim() -ieq 'C') { break }
                        $selected = $opText.Trim().ToUpperInvariant()
                        if ($selected -match '^[0-7]$') { $operation = [int]$selected }
                        elseif ($selected -in @('SUMA', 'A+B')) { $operation = 5 }
                        else { Write-Host 'Escriba un codigo entre 0 y 7, SUMA o A+B.' -ForegroundColor Yellow }
                    }
                    if ($null -eq $operation) { Write-Host 'Cancelado. No se modifico la ROM.'; break }
                    Write-Host ('OPERACION ELEGIDA: {0} (codigo {1})' -f $script:OperationNames[$operation], $operation) -ForegroundColor Cyan
                    $b = 0
                    if ($operation -in @(1, 3, 5, 6)) {
                        $b = Read-Operand 'Valor de B'
                        if ($null -eq $b) { Write-Host 'Cancelado. No se modifico la ROM.'; break }
                    }
                    else { Write-Host 'Esta operacion no utiliza B.' }
                    $words = @(New-IndependentOperationWords -A $a -Operation $operation -B $b)
                    Write-Host ('A=0x{0:X2} ({0} decimal); B=0x{1:X2} ({1} decimal).' -f $a, $b)
                    if ($operation -eq 5) {
                        $fullSum = $a + $b
                        Write-Host ('SUMA COMPLETA: 0x{0:X2} + 0x{1:X2} = 0x{2:X3}; ACC=0x{3:X2}; Cout={4}.' -f $a, $b, $fullSum, ($fullSum -band 255), [int]($fullSum -gt 255)) -ForegroundColor Cyan
                    }
                    Show-ProgramPreview -Words $words
                    $confirm = Read-Host 'ENTER guarda estos valores; cualquier texto cancela'
                    if ($confirm -ne '') { Write-Host 'Cancelado. No se modifico la ROM.'; break }
                    $null = Set-ProgramWords -Words $words -Root $Root
                    Write-Host 'Guardado. Compile y ejecute una simulacion nueva desde reset.' -ForegroundColor Green
                    Write-Host 'Consulte PROYECTO_INTEGRADO\CONFIGURACION_ACTUAL.txt para ver los resultados y pasos.'
                }
                '3' {
                    $null = Restore-OriginalProgram -Root $Root
                    Write-Host 'Programa original restaurado con los bytes exactos del respaldo.' -ForegroundColor Green
                    Write-Host 'Compile y ejecute una simulacion nueva para recuperar tambien los resultados originales.'
                }
                '4' {
                    $report = Update-CurrentConfiguration -Root $Root
                    Write-Host ''
                    Write-Host $report
                }
                default { Write-Host 'Seleccione 0, 1, 2, 3 o 4.' -ForegroundColor Yellow }
            }
        }
        catch {
            Write-Host ('ERROR: ' + $_.Exception.Message) -ForegroundColor Red
            Write-Host 'Revise la ruta y los permisos. La opcion 4 permite verificar el MIF activo.'
        }
    }
}

if ($MyInvocation.InvocationName -ne '.') {
    try { Start-FpgaMenu }
    catch { Write-Error $_; exit 1 }
}
