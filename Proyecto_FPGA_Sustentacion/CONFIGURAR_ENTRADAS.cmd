@echo off
setlocal
title Configurar entradas para la sustentacion FPGA
powershell.exe -NoLogo -NoProfile -ExecutionPolicy Bypass -File "%~dp0herramientas\Configurar-Entradas.ps1"
if errorlevel 1 (
    echo.
    echo No se pudo completar el configurador. Revise el mensaje anterior.
    pause
)
endlocal
