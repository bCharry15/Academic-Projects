
package com.mycompany.primvisualizer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class PrimVisualizer extends JFrame {
    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    private final List<Edge> mstEdges = new ArrayList<>();
    private Node selectedNode = null;
    private char nodeLabel = 'A';
    private double mstTotalWeight = 0;
    private boolean mostrarMST = false;

    private JTextArea descripcionCompleta;
    private JButton btnVerMas;
    private JPanel resultadoPanel;
    private JScrollPane tablaScroll;

    public PrimVisualizer() {
        setTitle("Visualizador Interactivo del Algoritmo de Prim");
        setSize(1300, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Algoritmo de Prim - Árbol de Expansión Mínima", JLabel.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 22));
        titulo.setForeground(new Color(0, 70, 140));

        descripcionCompleta = new JTextArea();
        descripcionCompleta.setEditable(false);
        descripcionCompleta.setOpaque(false);
        descripcionCompleta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descripcionCompleta.setForeground(Color.DARK_GRAY);
        descripcionCompleta.setText(getTextoCorto());

        btnVerMas = new JButton("Ver más");
        btnVerMas.addActionListener(e -> toggleDescripcion());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerPanel.add(titulo, BorderLayout.NORTH);
        headerPanel.add(descripcionCompleta, BorderLayout.CENTER);
        headerPanel.add(btnVerMas, BorderLayout.SOUTH);

        DrawPanel canvas = new DrawPanel();
        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Point clicked = e.getPoint();
                Node found = findNode(clicked);
                if (found == null) {
                    nodes.add(new Node(clicked, String.valueOf(nodeLabel++)));
                    selectedNode = null;
                    mstEdges.clear();
                    mostrarMST = false;
                } else {
                    if (selectedNode == null) {
                        selectedNode = found;
                    } else if (!selectedNode.equals(found)) {
                        if (!isConnected(selectedNode, found)) {
                            String input = JOptionPane.showInputDialog(
                                    canvas,
                                    "Ingrese el peso entero de la arista:",
                                    "Peso de Arista",
                                    JOptionPane.PLAIN_MESSAGE);
                            try {
                                if (input != null && !input.trim().isEmpty()) {
                                    int weight = Integer.parseInt(input.trim());
                                    if (weight < 0) throw new NumberFormatException();
                                    edges.add(new Edge(selectedNode, found, weight));
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(canvas, "Ingrese un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        selectedNode = null;
                        mstEdges.clear();
                        mostrarMST = false;
                    }
                }
                canvas.repaint();
            }
        });

        JButton btnCalcular = new JButton("Calcular PRIM");
        btnCalcular.setBackground(new Color(0, 150, 0));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFocusPainted(false);
        btnCalcular.addActionListener(e -> {
            calcularPrim();
            mostrarMST = true;
            canvas.repaint();
            actualizarTablaResultado();
        });

        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setBackground(new Color(200, 0, 0));
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.addActionListener(e -> {
            nodes.clear();
            edges.clear();
            mstEdges.clear();
            selectedNode = null;
            nodeLabel = 'A';
            mostrarMST = false;
            canvas.repaint();
            actualizarTablaResultado();
        });

        JButton btnEliminar = new JButton("Eliminar nodo");
        btnEliminar.setBackground(new Color(255, 165, 0));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFocusPainted(false);
        btnEliminar.addActionListener(e -> {
            if (selectedNode != null) {
                edges.removeIf(edge -> edge.from.equals(selectedNode) || edge.to.equals(selectedNode));
                nodes.remove(selectedNode);
                mstEdges.clear();
                selectedNode = null;
                mostrarMST = false;
                canvas.repaint();
                actualizarTablaResultado();
            } else {
                JOptionPane.showMessageDialog(canvas, "Selecciona un nodo primero haciendo clic sobre él.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton btnBuscar = new JButton("Buscar Nodo");
        btnBuscar.setBackground(new Color(0, 120, 200));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(e -> {
            String letra = JOptionPane.showInputDialog(canvas, "Ingrese la letra del nodo a buscar:");
            if (letra != null && letra.length() == 1) {
                Node found = nodes.stream()
                        .filter(n -> n.label.equalsIgnoreCase(letra))
                        .findFirst().orElse(null);
                if (found != null) {
                    selectedNode = found;
                    canvas.repaint();
                    JOptionPane.showMessageDialog(canvas, "Nodo '" + letra + "' encontrado.", "Buscar Nodo", JOptionPane.INFORMATION_MESSAGE);
                    selectedNode = null; // Deselecciona automáticamente
                    canvas.repaint();
                } else {
                    JOptionPane.showMessageDialog(canvas, "Nodo '" + letra + "' no encontrado.", "Buscar Nodo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton btnSalir = new JButton("Salir");
        btnSalir.setBackground(Color.GRAY);
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.addActionListener(e -> System.exit(0));

        JPanel controlPanel = new JPanel();
        controlPanel.add(btnCalcular);
        controlPanel.add(btnReiniciar);
        controlPanel.add(btnEliminar);
        controlPanel.add(btnBuscar);
        controlPanel.add(btnSalir);
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controles"));

        resultadoPanel = new JPanel(new BorderLayout());
        resultadoPanel.setPreferredSize(new Dimension(300, 0));
        resultadoPanel.setBorder(BorderFactory.createTitledBorder("Resultado del MST"));

        tablaScroll = new JScrollPane();
        resultadoPanel.add(tablaScroll, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        add(resultadoPanel, BorderLayout.EAST);
    }

    private void toggleDescripcion() {
        if (btnVerMas.getText().equals("Ver más")) {
            descripcionCompleta.setText(getTextoCompleto());
            btnVerMas.setText("Ver menos");
        } else {
            descripcionCompleta.setText(getTextoCorto());
            btnVerMas.setText("Ver más");
        }
    }

    private String getTextoCorto() {
        return "\nEl algoritmo de Prim es un algoritmo voraz para encontrar el Árbol de Expansión Mínima (MST) de un grafo.\n\n" +
                "Haz clic en el área para crear nodos, conéctalos y luego presiona 'Calcular PRIM' para ver el MST.";
    }

    private String getTextoCompleto() {
        return "\nEl algoritmo de Prim es un algoritmo voraz que se utiliza para encontrar el Árbol de Expansión Mínima (MST) " +
                "de un grafo conexo, no dirigido y ponderado. El MST es un subconjunto de las aristas del grafo que conecta todos los vértices\n " +
                "entre sí, sin ciclos y con el peso total mínimo.\n\n" +
                "Funcionamiento del algoritmo:\n" +
                "1. Selecciona un nodo de inicio arbitrario.\n" +
                "2. Marca este nodo como visitado.\n" +
                "3. Agrega la arista de menor peso que conecta un nodo visitado con uno no visitado.\n" +
                "4. Marca el nuevo nodo como visitado.\n" +
                "5. Repite hasta visitar todos los nodos.\n\n" +
                "Usos en la vida cotidiana:\n" +
                "- Redes eléctricas.\n" +
                "- Redes de computadoras.\n" +
                "- Tuberías o carreteras entre ciudades.\n" +
                "- Diseño de circuitos electrónicos.\n" +
                "- Ruteo eficiente de transporte urbano.";
    }

    private void actualizarTablaResultado() {
        String[] columnas = {"#", "Arista", "Peso"};
        Object[][] datos = new Object[mstEdges.size() + 1][3];

        for (int i = 0; i < mstEdges.size(); i++) {
            Edge e = mstEdges.get(i);
            datos[i][0] = i + 1;
            datos[i][1] = e.from.label + " - " + e.to.label;
            datos[i][2] = (int) e.weight;
        }
        datos[mstEdges.size()][0] = "";
        datos[mstEdges.size()][1] = "Total";
        datos[mstEdges.size()][2] = (int) mstTotalWeight;

        JTable tabla = new JTable(new DefaultTableModel(datos, columnas));
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabla.setRowHeight(24);

        tablaScroll.setViewportView(tabla);
    }

    private Node findNode(Point p) {
        for (Node node : nodes) {
            if (node.pos.distance(p) < 20) {
                return node;
            }
        }
        return null;
    }

    private boolean isConnected(Node a, Node b) {
        for (Edge e : edges) {
            if ((e.from.equals(a) && e.to.equals(b)) || (e.from.equals(b) && e.to.equals(a))) {
                return true;
            }
        }
        return false;
    }

    private void calcularPrim() {
        mstEdges.clear();
        mstTotalWeight = 0;
        if (nodes.size() < 2) return;

        Set<Node> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingDouble(e -> e.weight));
        Node start = nodes.get(0);
        visited.add(start);

        for (Edge e : edges) {
            if (e.from.equals(start) || e.to.equals(start)) pq.add(e);
        }

        while (!pq.isEmpty() && visited.size() < nodes.size()) {
            Edge e = pq.poll();
            Node newNode = null;

            if (visited.contains(e.from) && !visited.contains(e.to)) {
                newNode = e.to;
            } else if (visited.contains(e.to) && !visited.contains(e.from)) {
                newNode = e.from;
            }

            if (newNode != null) {
                mstEdges.add(e);
                mstTotalWeight += e.weight;
                visited.add(newNode);

                for (Edge edge : edges) {
                    if ((edge.from.equals(newNode) && !visited.contains(edge.to)) ||
                            (edge.to.equals(newNode) && !visited.contains(edge.from))) {
                        pq.add(edge);
                    }
                }
            }
        }
    }

    private class DrawPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));

            for (Edge e : edges) {
                g2.setColor(Color.GRAY);
                g2.drawLine(e.from.pos.x, e.from.pos.y, e.to.pos.x, e.to.pos.y);
                int midX = (e.from.pos.x + e.to.pos.x) / 2;
                int midY = (e.from.pos.y + e.to.pos.y) / 2;
                g2.setColor(Color.BLACK);
                g2.drawString(String.valueOf((int) e.weight), midX, midY);
            }

            if (mostrarMST) {
                g2.setColor(Color.RED);
                g2.setStroke(new BasicStroke(3));
                for (Edge e : mstEdges) {
                    g2.drawLine(e.from.pos.x, e.from.pos.y, e.to.pos.x, e.to.pos.y);
                }
                g2.setFont(new Font("SansSerif", Font.BOLD, 16));
                g2.drawString("Peso total del MST: " + mstTotalWeight, 20, 30);
            }

            for (Node node : nodes) {
                if (node.equals(selectedNode)) {
                    g2.setColor(Color.YELLOW);
                    g2.fillOval(node.pos.x - 20, node.pos.y - 20, 40, 40);
                }
                g2.setColor(new Color(100, 149, 237));
                g2.fillOval(node.pos.x - 18, node.pos.y - 18, 36, 36);
                g2.setColor(Color.BLACK);
                g2.drawOval(node.pos.x - 18, node.pos.y - 18, 36, 36);
                g2.setFont(new Font("Arial", Font.BOLD, 18));
                g2.drawString(node.label, node.pos.x - 6, node.pos.y + 6);
            }
        }
    }

    private static class Node {
        Point pos;
        String label;

        Node(Point pos, String label) {
            this.pos = pos;
            this.label = label;
        }
    }

    private static class Edge {
        Node from, to;
        double weight;

        Edge(Node from, Node to, double weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrimVisualizer().setVisible(true));
    }
}
