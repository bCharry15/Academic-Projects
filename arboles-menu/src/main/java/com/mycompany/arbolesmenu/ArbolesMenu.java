package com.mycompany.arbolesmenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class ArbolesMenu extends JFrame {
    
    private JPanel panelPrincipal;
    private JPanel panelSeleccionArbol;
    private JPanel panelOperaciones;
    private JPanel panelVisualizacion;
    
    private JButton btnArbolBinario;
    private JButton btnArbolAVL;
    private JButton btnArbolNArio;
    
    private JButton btnAgregarNodo;
    private JButton btnRecorridoInorden;
    private JButton btnRecorridoPreorden;
    private JButton btnRecorridoPostorden;
    private JButton btnBuscarNodo;
    private JButton btnEliminarNodo;
    private JButton btnVisualizarArbol;
    private JButton btnSalir;
    
    private JTextArea areaResultados;
    private JScrollPane scrollResultados;
    
    private Arbol arbolActual;
    private TipoArbol tipoArbolActual;
    
    
     private void limpiarVisualizacionArbol() {
    Component[] componentes = panelVisualizacion.getComponents();
    for (Component comp : componentes) {
        if (comp instanceof JScrollPane && ((JScrollPane) comp).getViewport().getView() != areaResultados) {
            panelVisualizacion.remove(comp);
        }
    }
    panelVisualizacion.revalidate();
    panelVisualizacion.repaint();
    }
     
    
    public enum TipoArbol {
        BINARIO, AVL, NARIO
    }
    
    public ArbolesMenu() {
        configurarVentana();
        inicializarComponentes();
        configurarEventos();
    }
    
    private void configurarVentana() {
        setTitle("Gestor de Árboles");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
    
    private void inicializarComponentes() {
        // Panel de selección de árbol
        panelSeleccionArbol = new JPanel();
        panelSeleccionArbol.setBorder(BorderFactory.createTitledBorder("Seleccione el tipo de árbol"));
        
        btnArbolBinario = new JButton("Árbol Binario");
        btnArbolAVL = new JButton("Árbol AVL");
        btnArbolNArio = new JButton("Árbol N-ario");
        
        panelSeleccionArbol.add(btnArbolBinario);
        panelSeleccionArbol.add(btnArbolAVL);
        panelSeleccionArbol.add(btnArbolNArio);
        
        // Panel de operaciones
        panelOperaciones = new JPanel();
        panelOperaciones.setBorder(BorderFactory.createTitledBorder("Operaciones    (presione cualquier opcion que desee realizar)"));
        panelOperaciones.setLayout(new GridLayout(4, 2, 10, 10));
        
        btnAgregarNodo = new JButton("1. Agregar Nodo");
        btnRecorridoInorden = new JButton("2. Mostrar Recorrido Inorden");
        btnRecorridoPreorden = new JButton("3. Mostrar Recorrido Preorden");
        btnRecorridoPostorden = new JButton("4. Mostrar Recorrido Postorden");
        btnBuscarNodo = new JButton("5. Buscar Nodo");
        btnEliminarNodo = new JButton("6. Eliminar Nodo");
        btnVisualizarArbol = new JButton("7. Visualizar Estructura del Árbol");
        btnSalir = new JButton("8. Salir");
        
        panelOperaciones.add(btnAgregarNodo);
        panelOperaciones.add(btnRecorridoInorden);
        panelOperaciones.add(btnRecorridoPreorden);
        panelOperaciones.add(btnRecorridoPostorden);
        panelOperaciones.add(btnBuscarNodo);
        panelOperaciones.add(btnEliminarNodo);
        panelOperaciones.add(btnVisualizarArbol);
        panelOperaciones.add(btnSalir);
        
        // Inicialmente, el panel de operaciones está deshabilitado
        habilitarOperaciones(false);
        
        // Panel de visualización
        panelVisualizacion = new JPanel(new BorderLayout());
        panelVisualizacion.setBorder(BorderFactory.createTitledBorder("Resultados"));
        
        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        scrollResultados = new JScrollPane(areaResultados);
        
        panelVisualizacion.add(scrollResultados, BorderLayout.CENTER);
        
        // Panel principal
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelSeleccionArbol, BorderLayout.NORTH);
        panelPrincipal.add(panelOperaciones, BorderLayout.WEST);
        panelPrincipal.add(panelVisualizacion, BorderLayout.CENTER);
        
        add(panelPrincipal);
    }
    
    private void configurarEventos() {
        // Eventos para los botones de selección de árbol
        btnArbolBinario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarVisualizacionArbol();
                arbolActual = new ArbolBinario();
                tipoArbolActual = TipoArbol.BINARIO;
                habilitarOperaciones(true);
                
                areaResultados.setText("Árbol Binario seleccionado.\n" +
                "Un árbol binario es una estructura de datos usada en informática y matemáticas.\n"
                +" para organizar información de forma jerárquica. " +
                "Se llama \"binario\" porque cada nodo puede tener como máximo dos hijos:.\n" +" "
                + " uno a la izquierda (menor que la raíz) y otro a la derecha (mayor que la raíz).\n");
            }
        });
        
        btnArbolAVL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarVisualizacionArbol();
                arbolActual = new ArbolAVL();
                tipoArbolActual = TipoArbol.AVL;
                habilitarOperaciones(true);
               areaResultados.setText("Árbol AVL seleccionado.\n" +
    "Un árbol AVL es un tipo de árbol binario de búsqueda que se mantiene balanceado automáticamente \n" +
    "Esto significa que después de cada inserción o eliminación de un nodo,\n" +
    " el árbol se reorganiza mediante rotaciones para que la diferencia de altura \n" +
    "entre los subárboles izquierdo y derecho de cualquier nodo nunca sea mayor a uno.\n" +
    "Gracias a este balanceo, las operaciones de búsqueda, inserción y eliminación se realizan de forma eficiente,\n" +
    " manteniendo un rendimiento óptimo.\n");

                
            }
        });
        
        btnArbolNArio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarVisualizacionArbol();
                arbolActual = new ArbolNArio();
                tipoArbolActual = TipoArbol.NARIO;
                habilitarOperaciones(true);
                areaResultados.setText("Árbol N-ario seleccionado.\n" +
    "Un árbol N-ario es una estructura de datos jerárquica donde cada nodo puede tener hasta N hijos,\n" +
    "a diferencia de un árbol binario que permite solo dos.\n" +
    "Este tipo de árbol es útil para representar información más compleja,\n" +
    "como estructuras de carpetas, jerarquías organizacionales o árboles de decisiones,\n" +
    "donde un elemento puede ramificarse en múltiples direcciones. " +
    "El valor de N puede variar según la aplicación, permitiendo mayor flexibilidad en la organización de datos.\n");

            }
        });
        
        // Eventos para los botones de operaciones
        btnAgregarNodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tipoArbolActual == TipoArbol.NARIO) {
                    String valorStr = JOptionPane.showInputDialog("Ingrese el valor del nuevo nodo:");
                    if (valorStr != null && !valorStr.isEmpty()) {
                        try {
                            int valor = Integer.parseInt(valorStr);
                            String padreStr = JOptionPane.showInputDialog("Ingrese el valor del nodo padre (deje en blanco para raíz):");
                            if (padreStr != null) {
                                if (padreStr.isEmpty()) {
                                    arbolActual.agregarNodo(valor, -1); // -1 indica raíz para N-ario
                                } else {
                                    try {
                                        int padre = Integer.parseInt(padreStr);
                                        arbolActual.agregarNodo(valor, padre);
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(null, "Valor del padre no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    String valorStr = JOptionPane.showInputDialog("Ingrese el valor del nuevo nodo:");
                    if (valorStr != null && !valorStr.isEmpty()) {
                        try {
                            int valor = Integer.parseInt(valorStr);
                            arbolActual.agregarNodo(valor, 0); // El segundo parámetro no importa para binario y AVL
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                mostrarVisualizacion();
            }
        });
        
        btnRecorridoInorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> recorrido = arbolActual.recorridoInorden();
                mostrarRecorrido("Recorrido Inorden" +"Se visita primero el subárbol izquierdo, luego el nodo raíz"+ 
                "y finalmente el subárbol derecho (Izquierda → Raíz → Derecha). \r\n" + //
                ":" , recorrido);
            }
        });
        
        btnRecorridoPreorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> recorrido = arbolActual.recorridoPreorden();
                mostrarRecorrido("Recorrido Preorden..." + "Se visita primero el nodo raíz, luego el subárbol izquierdo"+
                " y finalmente el subárbol derecho (Raíz → Izquierda → Derecha). \r\n" + //
                                        ":", recorrido);
            }
        });
        
        btnRecorridoPostorden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> recorrido = arbolActual.recorridoPostorden();
                mostrarRecorrido("Recorrido Postorden" + " Se visita primero el subárbol izquierdo, luego el subárbol derecho"+ 
                "y finalmente el nodo raíz (Izquierda → Derecha → Raíz). \r\n" + //
                ":", recorrido);
            }
        });
        
        btnBuscarNodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valorStr = JOptionPane.showInputDialog("Ingrese el valor a buscar:");
                if (valorStr != null && !valorStr.isEmpty()) {
                    try {
                        int valor = Integer.parseInt(valorStr);
                        boolean encontrado = arbolActual.buscarNodo(valor);
                        if (encontrado) {
                            areaResultados.append("Nodo " + valor + " encontrado en el árbol.\n");
                        } else {
                            areaResultados.append("Nodo " + valor + " no encontrado.\n");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        btnEliminarNodo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valorStr = JOptionPane.showInputDialog("Ingrese el valor a eliminar:");
                if (valorStr != null && !valorStr.isEmpty()) {
                    try {
                        int valor = Integer.parseInt(valorStr);
                        boolean eliminado = arbolActual.eliminarNodo(valor);
                        if (eliminado) {
                            areaResultados.append("Nodo " + valor + " eliminado.\n");
                            mostrarVisualizacion();
                        } else {
                            areaResultados.append("Nodo " + valor + " no encontrado para eliminar.\n");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Valor no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        btnVisualizarArbol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVisualizacion();
            }
        });
        
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    private void habilitarOperaciones(boolean habilitar) {
        btnAgregarNodo.setEnabled(habilitar);
        btnRecorridoInorden.setEnabled(habilitar);
        btnRecorridoPreorden.setEnabled(habilitar);
        btnRecorridoPostorden.setEnabled(habilitar);
        btnBuscarNodo.setEnabled(habilitar);
        btnEliminarNodo.setEnabled(habilitar);
        btnVisualizarArbol.setEnabled(habilitar);
    }
    
    private void mostrarRecorrido(String tipoRecorrido, List<Integer> recorrido) {
        StringBuilder sb = new StringBuilder(tipoRecorrido + ": ");
        for (int i = 0; i < recorrido.size(); i++) {
            sb.append(recorrido.get(i));
            if (i < recorrido.size() - 1) {
                sb.append(" -> ");
            }
        }
        areaResultados.append(sb.toString() + "\n");
    }
    
private void mostrarVisualizacion() {
    areaResultados.append("Visualizando estructura del árbol...\n");
    
    // Remove previous visualization panel if it exists
    for (Component comp : panelVisualizacion.getComponents()) {
        if (comp instanceof JScrollPane && ((JScrollPane) comp).getViewport().getView() != areaResultados) {
            panelVisualizacion.remove(comp);
        }
    }
    
    // Create and add the tree visualization panel
    JPanel treePanel = null;
    
    if (arbolActual instanceof ArbolBinario) {
        treePanel = ((ArbolBinario) arbolActual).dibujarArbol();
    } else if (arbolActual instanceof ArbolAVL) {
        treePanel = ((ArbolAVL) arbolActual).dibujarArbol();
    } else if (arbolActual instanceof ArbolNArio) {
        // Ahora usamos la visualización gráfica también para árboles N-arios
        treePanel = ((ArbolNArio) arbolActual).dibujarArbol();
    }
    
    if (treePanel != null) {
        JScrollPane scrollTree = new JScrollPane(treePanel);
        panelVisualizacion.add(scrollTree, BorderLayout.NORTH);
        panelVisualizacion.revalidate();
        panelVisualizacion.repaint();
    }
}
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ArbolesMenu().setVisible(true);
            }
        });
    }


    // Interfaz Arbol para todos los tipos de árboles
    private interface Arbol {
        void agregarNodo(int valor, int padre);
        boolean eliminarNodo(int valor);
        boolean buscarNodo(int valor);
        List<Integer> recorridoInorden();
        List<Integer> recorridoPreorden();
        List<Integer> recorridoPostorden();
        String visualizarArbol();
    }
    
    // Implementación del Árbol Binario
    private class ArbolBinario implements Arbol {
        private NodoBinario raiz;
        
        private class NodoBinario {
            int valor;
            NodoBinario izquierdo;
            NodoBinario derecho;
            
            public NodoBinario(int valor) {
                this.valor = valor;
                this.izquierdo = null;
                this.derecho = null;
            }
        }
        
        public ArbolBinario() {
            this.raiz = null;
        }
        
        @Override
        public void agregarNodo(int valor, int padre) {
            raiz = agregarRecursivo(raiz, valor);
        }
        
        private NodoBinario agregarRecursivo(NodoBinario nodo, int valor) {
            if (nodo == null) {
                return new NodoBinario(valor);
            }
            
            if (valor < nodo.valor) {
                nodo.izquierdo = agregarRecursivo(nodo.izquierdo, valor);
            } else if (valor > nodo.valor) {
                nodo.derecho = agregarRecursivo(nodo.derecho, valor);
            }
            
            return nodo;
        }
        
        @Override
        public boolean eliminarNodo(int valor) {
            int[] eliminado = {0}; // Para rastrear si se eliminó un nodo
            raiz = eliminarRecursivo(raiz, valor, eliminado);
            return eliminado[0] == 1;
        }
        
        private NodoBinario eliminarRecursivo(NodoBinario nodo, int valor, int[] eliminado) {
            if (nodo == null) {
                return null;
            }
            
            if (valor < nodo.valor) {
                nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, valor, eliminado);
            } else if (valor > nodo.valor) {
                nodo.derecho = eliminarRecursivo(nodo.derecho, valor, eliminado);
            } else {
                eliminado[0] = 1; // Marcamos que se eliminó un nodo
                
                // Nodo con un hijo o sin hijos
                if (nodo.izquierdo == null) {
                    return nodo.derecho;
                } else if (nodo.derecho == null) {
                    return nodo.izquierdo;
                }
                
                // Nodo con dos hijos
                nodo.valor = encontrarMinimoValor(nodo.derecho);
                nodo.derecho = eliminarRecursivo(nodo.derecho, nodo.valor, new int[1]);
            }
            
            return nodo;
        }
        
        private int encontrarMinimoValor(NodoBinario nodo) {
            int valorMinimo = nodo.valor;
            while (nodo.izquierdo != null) {
                valorMinimo = nodo.izquierdo.valor;
                nodo = nodo.izquierdo;
            }
            return valorMinimo;
        }
        
        @Override
        public boolean buscarNodo(int valor) {
            return buscarRecursivo(raiz, valor);
        }
        
        private boolean buscarRecursivo(NodoBinario nodo, int valor) {
            if (nodo == null) {
                return false;
            }
            
            if (nodo.valor == valor) {
                return true;
            }
            
            if (valor < nodo.valor) {
                return buscarRecursivo(nodo.izquierdo, valor);
            }
            
            return buscarRecursivo(nodo.derecho, valor);
        }
        
        @Override
        public List<Integer> recorridoInorden() {
            List<Integer> resultado = new ArrayList<>();
            inordenRecursivo(raiz, resultado);
            return resultado;
        }
        
        private void inordenRecursivo(NodoBinario nodo, List<Integer> resultado) {
            if (nodo != null) {
                inordenRecursivo(nodo.izquierdo, resultado);
                resultado.add(nodo.valor);
                inordenRecursivo(nodo.derecho, resultado);
            }
        }
        
        @Override
        public List<Integer> recorridoPreorden() {
            List<Integer> resultado = new ArrayList<>();
            preordenRecursivo(raiz, resultado);
            return resultado;
        }
        
        private void preordenRecursivo(NodoBinario nodo, List<Integer> resultado) {
            if (nodo != null) {
                resultado.add(nodo.valor);
                preordenRecursivo(nodo.izquierdo, resultado);
                preordenRecursivo(nodo.derecho, resultado);
            }
        }
        
        @Override
        public List<Integer> recorridoPostorden() {
            List<Integer> resultado = new ArrayList<>();
            postordenRecursivo(raiz, resultado);
            return resultado;
        }
        
        private void postordenRecursivo(NodoBinario nodo, List<Integer> resultado) {
            if (nodo != null) {
                postordenRecursivo(nodo.izquierdo, resultado);
                postordenRecursivo(nodo.derecho, resultado);
                resultado.add(nodo.valor);
            }
        }
        
        // Método mejorado para visualizar el árbol de manera más clara y natural
        @Override
        
public String visualizarArbol() {
    if (raiz == null) {
        return "Árbol vacío";
    }
    
    return "Utilice el botón 'Visualizar Estructura del Árbol' para ver el árbol gráficamente";
}

// And add a method to draw the tree graphically
public JPanel dibujarArbol() {
    return new TreePanel(raiz);
}

// Add this inner class inside ArbolBinario
private class TreePanel extends JPanel {
    private NodoBinario raiz;
    private int nodeSize = 30;
    private int horizontalGap = 30;
    private int verticalGap = 40;
    private final Color nodeColor = new Color(0,255 , 0); // Yellow color like in your image
    private final Color textColor = Color.BLACK;
    private final Color lineColor = Color.ORANGE;
    
    public TreePanel(NodoBinario raiz) {
        this.raiz = raiz;
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.WHITE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (raiz != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int treeHeight = obtenerAltura(raiz);
            int panelWidth = getWidth();
            
            dibujarNodo(g2d, raiz, panelWidth / 2, 50, panelWidth / (int)Math.pow(2, 3), treeHeight);
        }
    }
    
    private void dibujarNodo(Graphics2D g, NodoBinario nodo, int x, int y, int xOffset, int treeHeight) {
        if (nodo == null) return;
        
        // Dibujar las líneas a los hijos
        if (nodo.izquierdo != null) {
            int childX = x - xOffset;
            int childY = y + verticalGap;
            g.setColor(lineColor);
            g.drawLine(x, y + nodeSize/2, childX, childY + nodeSize/2);
            dibujarNodo(g, nodo.izquierdo, childX, childY, xOffset/2, treeHeight - 1);
        }
        
        if (nodo.derecho != null) {
            int childX = x + xOffset;
            int childY = y + verticalGap;
            g.setColor(lineColor);
            g.drawLine(x, y + nodeSize/2, childX, childY + nodeSize/2);
            dibujarNodo(g, nodo.derecho, childX, childY, xOffset/2, treeHeight - 1);
        }
        
        // Dibujar el círculo del nodo
        g.setColor(nodeColor);
        g.fillOval(x - nodeSize/2, y, nodeSize, nodeSize);
        g.setColor(Color.BLACK);
        g.drawOval(x - nodeSize/2, y, nodeSize, nodeSize);
        
        // Dibujar el valor del nodo
        g.setColor(textColor);
        String valorStr = String.valueOf(nodo.valor);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(valorStr);
        int textHeight = fm.getHeight();
        g.drawString(valorStr, x - textWidth/2, y + nodeSize/2 + textHeight/4);
    }
}
        
        // Método auxiliar para obtener la altura del árbol
        private int obtenerAltura(NodoBinario nodo) {
            if (nodo == null) {
                return 0;
            }
            return 1 + Math.max(obtenerAltura(nodo.izquierdo), obtenerAltura(nodo.derecho));
        }
        
        // Método para llenar las listas con los valores de los nodos
        // Dentro de la clase ArbolAVL, modifica el método llenarNiveles
        // Método para llenar las listas con los valores de los nodos
        private void llenarNiveles(NodoBinario nodo, int nivel, ArrayList<ArrayList<String>> niveles, int maxNivel) {
            if (nodo == null) {
                return;
            }
            
            // Agregar el valor del nodo a la lista correspondiente
            niveles.get(nivel).add(String.valueOf(nodo.valor));
            
            // Procesar los hijos
            llenarNiveles(nodo.izquierdo, nivel + 1, niveles, maxNivel);
            llenarNiveles(nodo.derecho, nivel + 1, niveles, maxNivel);
        }
    }
    
    // Implementación del Árbol AVL
    private class ArbolAVL implements Arbol {
        private NodoAVL raiz;
        
        private class NodoAVL {
            int valor;
            NodoAVL izquierdo;
            NodoAVL derecho;
            int altura;
            
            public NodoAVL(int valor) {
                this.valor = valor;
                this.altura = 1;
                this.izquierdo = null;
                this.derecho = null;
            }
        }
        
        public ArbolAVL() {
            this.raiz = null;
        }
        
        private int altura(NodoAVL nodo) {
            if (nodo == null) {
                return 0;
            }
            return nodo.altura;
        }
        
        private int max(int a, int b) {
            return Math.max(a, b);
        }
        
        private int getBalance(NodoAVL nodo) {
            if (nodo == null) {
                return 0;
            }
            return altura(nodo.izquierdo) - altura(nodo.derecho);
        }
        
        private NodoAVL rotacionDerecha(NodoAVL y) {
            NodoAVL x = y.izquierdo;
            NodoAVL T2 = x.derecho;
            
            x.derecho = y;
            y.izquierdo = T2;
            
            y.altura = max(altura(y.izquierdo), altura(y.derecho)) + 1;
            x.altura = max(altura(x.izquierdo), altura(x.derecho)) + 1;
            
            return x;
        }
        
        private NodoAVL rotacionIzquierda(NodoAVL x) {
            NodoAVL y = x.derecho;
            NodoAVL T2 = y.izquierdo;
            
            y.izquierdo = x;
            x.derecho = T2;
            
            x.altura = max(altura(x.izquierdo), altura(x.derecho)) + 1;
            y.altura = max(altura(y.izquierdo), altura(y.derecho)) + 1;
            
            return y;
        }
        
        @Override
        public void agregarNodo(int valor, int padre) {
            raiz = agregarRecursivo(raiz, valor);
        }
        
        private NodoAVL agregarRecursivo(NodoAVL nodo, int valor) {
            if (nodo == null) {
                return new NodoAVL(valor);
            }
            
            if (valor < nodo.valor) {
                nodo.izquierdo = agregarRecursivo(nodo.izquierdo, valor);
            } else if (valor > nodo.valor) {
                nodo.derecho = agregarRecursivo(nodo.derecho, valor);
            } else {
                return nodo; // No se permiten valores duplicados
            }
            
            // Actualizar altura del nodo actual
            nodo.altura = 1 + max(altura(nodo.izquierdo), altura(nodo.derecho));
            
            // Obtener el factor de balance
            int balance = getBalance(nodo);
            
            // Casos de desequilibrio
            
            // Izquierda Izquierda
            if (balance > 1 && valor < nodo.izquierdo.valor) {
                return rotacionDerecha(nodo);
            }
            
            // Derecha Derecha
            if (balance < -1 && valor > nodo.derecho.valor) {
                return rotacionIzquierda(nodo);
            }
            
            // Izquierda Derecha
            if (balance > 1 && valor > nodo.izquierdo.valor) {
                nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
                return rotacionDerecha(nodo);
            }
            
            // Derecha Izquierda
            if (balance < -1 && valor < nodo.derecho.valor) {
                nodo.derecho = rotacionDerecha(nodo.derecho);
                return rotacionIzquierda(nodo);
            }
            
            return nodo;
        }
        
        @Override
        public boolean eliminarNodo(int valor) {
            int[] eliminado = {0}; // Para rastrear si se eliminó un nodo
            raiz = eliminarRecursivo(raiz, valor, eliminado);
            return eliminado[0] == 1;
        }
        
        private NodoAVL eliminarRecursivo(NodoAVL nodo, int valor, int[] eliminado) {
            if (nodo == null) {
                return null;
            }
            
            if (valor < nodo.valor) {
                nodo.izquierdo = eliminarRecursivo(nodo.izquierdo, valor, eliminado);
            } else if (valor > nodo.valor) {
                nodo.derecho = eliminarRecursivo(nodo.derecho, valor, eliminado);
            } else {
                eliminado[0] = 1; // Marcamos que se eliminó un nodo
                
                // Nodo con un hijo o sin hijos
                if (nodo.izquierdo == null || nodo.derecho == null) {
                    NodoAVL temp = null;
                    if (temp == nodo.izquierdo) {
                        temp = nodo.derecho;
                    } else {
                        temp = nodo.izquierdo;
                    }
                    
                    // Sin hijos
                    if (temp == null) {
                        temp = nodo;
                        nodo = null;
                    } else { // Un hijo
                        nodo = temp;
                    }
                } else {
                    // Nodo con dos hijos
                    NodoAVL temp = encontrarNodoMinimoValor(nodo.derecho);
                    nodo.valor = temp.valor;
                    nodo.derecho = eliminarRecursivo(nodo.derecho, temp.valor, new int[1]);
                }
            }
            
            // Si el árbol tenía solo un nodo
            if (nodo == null) {
                return null;
            }
            
            // Actualizar altura
            nodo.altura = max(altura(nodo.izquierdo), altura(nodo.derecho)) + 1;
            
            // Obtener el factor de balance
            int balance = getBalance(nodo);
            
            // Casos de desequilibrio
            
            // Izquierda Izquierda
            if (balance > 1 && getBalance(nodo.izquierdo) >= 0) {
                return rotacionDerecha(nodo);
            }
            
            // Izquierda Derecha
            if (balance > 1 && getBalance(nodo.izquierdo) < 0) {
                nodo.izquierdo = rotacionIzquierda(nodo.izquierdo);
                return rotacionDerecha(nodo);
            }
            
            // Derecha Derecha
            if (balance < -1 && getBalance(nodo.derecho) <= 0) {
                return rotacionIzquierda(nodo);
            }
            
            // Derecha Izquierda
            if (balance < -1 && getBalance(nodo.derecho) > 0) {
                nodo.derecho = rotacionDerecha(nodo.derecho);
                return rotacionIzquierda(nodo);
            }
            
            return nodo;
        }
        
        private NodoAVL encontrarNodoMinimoValor(NodoAVL nodo) {
            NodoAVL actual = nodo;
            while (actual.izquierdo != null) {
                actual = actual.izquierdo;
            }
            return actual;
        }
        
        @Override
        public boolean buscarNodo(int valor) {
            return buscarRecursivo(raiz, valor);
        }
        
        private boolean buscarRecursivo(NodoAVL nodo, int valor) {
            if (nodo == null) {
                return false;
            }
            
            if (nodo.valor == valor) {
                return true;
            }
            
            if (valor < nodo.valor) {
                return buscarRecursivo(nodo.izquierdo, valor);
            }
            
            return buscarRecursivo(nodo.derecho, valor);
        }
        
        @Override
        public List<Integer> recorridoInorden() {
            List<Integer> resultado = new ArrayList<>();
            inordenRecursivo(raiz, resultado);
            return resultado;
        }
        
        private void inordenRecursivo(NodoAVL nodo, List<Integer> resultado) {
            if (nodo != null) {
                inordenRecursivo(nodo.izquierdo, resultado);
                resultado.add(nodo.valor);
                inordenRecursivo(nodo.derecho, resultado);
            }
        }
        
        @Override
        public List<Integer> recorridoPreorden() {
            List<Integer> resultado = new ArrayList<>();
            preordenRecursivo(raiz, resultado);
            return resultado;
        }
        
        private void preordenRecursivo(NodoAVL nodo, List<Integer> resultado) {
            if (nodo != null) {
                resultado.add(nodo.valor);
                preordenRecursivo(nodo.izquierdo, resultado);
                preordenRecursivo(nodo.derecho, resultado);
            }
        }
        
        @Override
        public List<Integer> recorridoPostorden() {
            List<Integer> resultado = new ArrayList<>();
            postordenRecursivo(raiz, resultado);
            return resultado;
        }
        
        private void postordenRecursivo(NodoAVL nodo, List<Integer> resultado) {
            if (nodo != null) {
                postordenRecursivo(nodo.izquierdo, resultado);
                postordenRecursivo(nodo.derecho, resultado);
                resultado.add(nodo.valor);
            }
        }
        
        @Override
       
public String visualizarArbol() {
    if (raiz == null) {
        return "Árbol vacío";
    }
    
    return "Utilice el botón 'Visualizar Estructura del Árbol' para ver el árbol gráficamente";
}

// And add a method to draw the tree graphically
public JPanel dibujarArbol() {
    return new TreePanel(raiz);
}

// Add this inner class inside ArbolAVL
private class TreePanel extends JPanel {
    private NodoAVL raiz;
    private int nodeSize = 30;
    private int horizontalGap = 40;
    private int verticalGap = 50;
    private final Color nodeColor = new Color(0, 255, 0); // Yellow color like in your image
    private final Color textColor = Color.BLACK;
    private final Color lineColor = Color.ORANGE;
    
    public TreePanel(NodoAVL raiz) {
        this.raiz = raiz;
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.WHITE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (raiz != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int treeHeight = obtenerAltura(raiz);
            int panelWidth = getWidth();
            
            dibujarNodo(g2d, raiz, panelWidth / 2, 50, panelWidth / (int)Math.pow(2, 3), treeHeight);
        }
    }
    
    private void dibujarNodo(Graphics2D g, NodoAVL nodo, int x, int y, int xOffset, int treeHeight) {
        if (nodo == null) return;
        
        // Dibujar las líneas a los hijos
        if (nodo.izquierdo != null) {
            int childX = x - xOffset;
            int childY = y + verticalGap;
            g.setColor(lineColor);
            g.drawLine(x, y + nodeSize/2, childX, childY + nodeSize/2);
            dibujarNodo(g, nodo.izquierdo, childX, childY, xOffset/2, treeHeight - 1);
        }
        
        if (nodo.derecho != null) {
            int childX = x + xOffset;
            int childY = y + verticalGap;
            g.setColor(lineColor);
            g.drawLine(x, y + nodeSize/2, childX, childY + nodeSize/2);
            dibujarNodo(g, nodo.derecho, childX, childY, xOffset/2, treeHeight - 1);
        }
        
        // Dibujar el círculo del nodo
        g.setColor(nodeColor);
        g.fillOval(x - nodeSize/2, y, nodeSize, nodeSize);
        g.setColor(Color.BLACK);
        g.drawOval(x - nodeSize/2, y, nodeSize, nodeSize);
        
        // Dibujar el valor del nodo
        g.setColor(textColor);
        String valorStr = String.valueOf(nodo.valor);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(valorStr);
        int textHeight = fm.getHeight();
        g.drawString(valorStr, x - textWidth/2, y + nodeSize/2 + textHeight/4);
    }
}
        
        // Método auxiliar para obtener la altura del árbol
        private int obtenerAltura(NodoAVL nodo) {
            if (nodo == null) {
                return 0;
            }
            return 1 + Math.max(obtenerAltura(nodo.izquierdo), obtenerAltura(nodo.derecho));
        }
        
        // Método para llenar las listas con los valores de los nodos
        private void llenarNiveles(NodoAVL nodo, int nivel, ArrayList<ArrayList<String>> niveles, int maxNivel) {
            if (nodo == null) {
                return;
            }
            
            // Agregar el valor del nodo a la lista correspondiente
            niveles.get(nivel).add(String.valueOf(nodo.valor));
            
            // Procesar los hijos
            llenarNiveles(nodo.izquierdo, nivel + 1, niveles, maxNivel);
            llenarNiveles(nodo.derecho, nivel + 1, niveles, maxNivel);
        }
    }
    
    // Implementación del Árbol N-ario
    private class ArbolNArio implements Arbol {
        private NodoNArio raiz;
        
        private class NodoNArio {
            int valor;
            List<NodoNArio> hijos;
            
            public NodoNArio(int valor) {
                this.valor = valor;
                this.hijos = new ArrayList<>();
            }
        }
        
        public ArbolNArio() {
            this.raiz = null;
        }
        // Dentro de la clase ArbolNArio, añade este método:
public JPanel dibujarArbol() {
    return new TreePanel(raiz);
}

// Añade esta clase interna dentro de ArbolNArio
private class TreePanel extends JPanel {
    private NodoNArio raiz;
    private int nodeSize = 30;
    private int horizontalGap = 20;
    private int verticalGap = 40;
    private final Color nodeColor = new Color(0, 255, 0);
    private final Color textColor = Color.BLACK;
    private final Color lineColor = Color.ORANGE;
    
    public TreePanel(NodoNArio raiz) {
        this.raiz = raiz;
        setPreferredSize(new Dimension(800, 400));
        setBackground(Color.WHITE);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (raiz != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Obtener el ancho total disponible
            int panelWidth = getWidth();
            
            // Dibujar el árbol empezando por la raíz
            dibujarNodo(g2d, raiz, panelWidth / 2, 50, panelWidth);
        }
    }
    
    private void dibujarNodo(Graphics2D g, NodoNArio nodo, int x, int y, int availableWidth) {
        if (nodo == null) return;
        
        // Dibujar el círculo del nodo
        g.setColor(nodeColor);
        g.fillOval(x - nodeSize/2, y, nodeSize, nodeSize);
        g.setColor(Color.BLACK);
        g.drawOval(x - nodeSize/2, y, nodeSize, nodeSize);
        
        // Dibujar el valor del nodo
        g.setColor(textColor);
        String valorStr = String.valueOf(nodo.valor);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(valorStr);
        int textHeight = fm.getHeight();
        g.drawString(valorStr, x - textWidth/2, y + nodeSize/2 + textHeight/4);
        
        // Si el nodo tiene hijos
        if (nodo.hijos.size() > 0) {
            // Calcular el ancho para cada hijo
            int childWidth = availableWidth / Math.max(1, nodo.hijos.size());
            int startX = x - (childWidth * (nodo.hijos.size() - 1)) / 2;
            
            // Dibujar cada hijo
            for (int i = 0; i < nodo.hijos.size(); i++) {
                int childX = startX + i * childWidth;
                int childY = y + verticalGap;
                
                // Dibujar la línea al hijo
                g.setColor(lineColor);
                g.drawLine(x, y + nodeSize/2, childX, childY + nodeSize/2);
                
                // Dibujar el hijo recursivamente
                dibujarNodo(g, nodo.hijos.get(i), childX, childY, childWidth);
            }
        }
    }
}

// También debes modificar el método visualizarArbol() dentro de ArbolNArio:
@Override
public String visualizarArbol() {
    if (raiz == null) {
        return "Árbol vacío";
    }
    
    return "Utilice el botón 'Visualizar Estructura del Árbol' para ver el árbol gráficamente";
}
        @Override
        public void agregarNodo(int valor, int padre) {
            // Si el árbol está vacío o se indica -1 como padre (raíz)
            if (raiz == null || padre == -1) {
                raiz = new NodoNArio(valor);
                return;
            }
            
            // Buscar el nodo padre y agregar el nuevo nodo como hijo
            NodoNArio nodoPadre = buscarNodoInterno(raiz, padre);
            if (nodoPadre != null) {
                nodoPadre.hijos.add(new NodoNArio(valor));
            } else {
                JOptionPane.showMessageDialog(null, "Nodo padre no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        private NodoNArio buscarNodoInterno(NodoNArio nodo, int valor) {
            if (nodo == null) {
                return null;
            }
            
            if (nodo.valor == valor) {
                return nodo;
            }
            
            for (NodoNArio hijo : nodo.hijos) {
                NodoNArio encontrado = buscarNodoInterno(hijo, valor);
                if (encontrado != null) {
                    return encontrado;
                }
            }
            
            return null;
        }
        
        @Override
        public boolean eliminarNodo(int valor) {
            if (raiz == null) {
                return false;
            }
            
            // Si el nodo a eliminar es la raíz
            if (raiz.valor == valor) {
                raiz = null;
                return true;
            }
            
            return eliminarNodoRecursivo(raiz, valor);
        }
        
        private boolean eliminarNodoRecursivo(NodoNArio nodo, int valor) {
            // Verificar en los hijos directos
            for (int i = 0; i < nodo.hijos.size(); i++) {
                if (nodo.hijos.get(i).valor == valor) {
                    nodo.hijos.remove(i);
                    return true;
                }
            }
            
            // Buscar en los hijos de manera recursiva
            for (NodoNArio hijo : nodo.hijos) {
                if (eliminarNodoRecursivo(hijo, valor)) {
                    return true;
                }
            }
            
            return false;
        }
        
        @Override
        public boolean buscarNodo(int valor) {
            return buscarNodoInterno(raiz, valor) != null;
        }
        
        @Override
        public List<Integer> recorridoInorden() {
            List<Integer> resultado = new ArrayList<>();
            inordenRecursivo(raiz, resultado);
            return resultado;
        }
        
        private void inordenRecursivo(NodoNArio nodo, List<Integer> resultado) {
            if (nodo == null) {
                return;
            }
            
            if (!nodo.hijos.isEmpty()) {
                // Recorrer la primera mitad de los hijos
                int mitad = nodo.hijos.size() / 2;
                for (int i = 0; i < mitad; i++) {
                    inordenRecursivo(nodo.hijos.get(i), resultado);
                }
                
                // Visitar el nodo actual
                resultado.add(nodo.valor);
                
                // Recorrer la segunda mitad de los hijos
                for (int i = mitad; i < nodo.hijos.size(); i++) {
                    inordenRecursivo(nodo.hijos.get(i), resultado);
                }
            } else {
                // Si no tiene hijos, simplemente visitar el nodo
                resultado.add(nodo.valor);
            }
        }
        
        @Override
        public List<Integer> recorridoPreorden() {
            List<Integer> resultado = new ArrayList<>();
            preordenRecursivo(raiz, resultado);
            return resultado;
        }
        
        private void preordenRecursivo(NodoNArio nodo, List<Integer> resultado) {
            if (nodo == null) {
                return;
            }
            
            // Visitar primero el nodo actual
            resultado.add(nodo.valor);
            
            // Luego visitar todos los hijos
            for (NodoNArio hijo : nodo.hijos) {
                preordenRecursivo(hijo, resultado);
            }
        }
        
        @Override
        public List<Integer> recorridoPostorden() {
            List<Integer> resultado = new ArrayList<>();
            postordenRecursivo(raiz, resultado);
            return resultado;
        }
        
        private void postordenRecursivo(NodoNArio nodo, List<Integer> resultado) {
            if (nodo == null) {
                return;
            }
            
            // Primero visitar todos los hijos
            for (NodoNArio hijo : nodo.hijos) {
                postordenRecursivo(hijo, resultado);
            }
            
            // Luego visitar el nodo actual
            resultado.add(nodo.valor);
        }
    
        private void visualizarRecursivo(NodoNArio nodo, int nivel, StringBuilder sb) {
            if (nodo == null) {
                return;
            }
            
            // Indentación según el nivel
            for (int i = 0; i < nivel; i++) {
                sb.append("    ");
            }
            
            // Agregar el valor del nodo actual
            sb.append(nodo.valor).append("\n");
            
            // Recorrer los hijos
            for (NodoNArio hijo : nodo.hijos) {
                visualizarRecursivo(hijo, nivel + 1, sb);
            }
        }
    }
}