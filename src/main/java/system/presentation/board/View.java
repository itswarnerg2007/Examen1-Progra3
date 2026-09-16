package system.presentation.board;

import system.logic.entities.*;
import javax.swing.*;
import java.awt.*;
import java.beans.*;
import java.util.List;

public class View implements PropertyChangeListener {

    private JPanel panel;

    private JTable Tabla3;
    private JTable Tabla2;
    private JTable TablaDeducciones;

    private JComboBox<Bonos> GradoMaestriacomboBox;
    private JComboBox<Deducciones> SegurodevidacomboBox;

    private JTextField CedulatextField;
    private JTextField NombretextField;
    private JTextField TelefonoTextField;
    private JTextField CorreoTextField;
    private JTextField SalariotextField;

    private JButton agregarButton;
    private JButton agregarButton1;
    private JButton agregarButton2;
    private JButton modificarButton;
    private JButton limpiarButton;

    private Model model;
    private Controller controller;

    public View() {

        if (panel == null) {
            buildFallbackUI();
        }

        modificarButton.setEnabled(false);

        agregarButton.addActionListener(e ->
                controller.addBonus(
                        (Bonos) GradoMaestriacomboBox.getSelectedItem()
                )
        );

        agregarButton1.addActionListener(e ->
                controller.addDeduction(
                        (Deducciones) SegurodevidacomboBox.getSelectedItem()
                )
        );

        agregarButton2.addActionListener(e ->
                addEmployee()
        );

        modificarButton.addActionListener(e ->
                modifyEmployee()
        );

        limpiarButton.addActionListener(e ->
                controller.clear()
        );

        Tabla3.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        Tabla3.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()
                    && Tabla3.getSelectedRow() >= 0
                    && Tabla3.getModel() instanceof EmpleadoTableModel tm) {

                int row = Tabla3.convertRowIndexToModel(
                        Tabla3.getSelectedRow()
                );

                controller.selectEmployee(
                        tm.getRowAt(row)
                );
            }
        });

        // Doble clic para cargar empleado
        Tabla3.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {

                        if (e.getClickCount() == 2
                                && Tabla3.getSelectedRow() >= 0
                                && Tabla3.getModel() instanceof EmpleadoTableModel tm) {

                            int row = Tabla3.convertRowIndexToModel(
                                    Tabla3.getSelectedRow()
                            );

                            controller.selectEmployee(
                                    tm.getRowAt(row)
                            );
                        }
                    }
                }
        );

        // =========================
        // TABLA DE BONOS
        // =========================

        Tabla2.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {

                        if (e.getClickCount() == 2
                                && Tabla2.getSelectedRow() >= 0
                                && Tabla2.getModel() instanceof BonoTableModel tm) {

                            int row = Tabla2.convertRowIndexToModel(
                                    Tabla2.getSelectedRow()
                            );

                            controller.removeBonus(
                                    tm.getRowAt(row)
                            );
                        }
                    }
                }
        );

        // =========================
        // TABLA DE DEDUCCIONES
        // =========================

        TablaDeducciones.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {

                        if (e.getClickCount() == 2
                                && TablaDeducciones.getSelectedRow() >= 0
                                && TablaDeducciones.getModel() instanceof DeduccionTableModel tm) {

                            int row = TablaDeducciones.convertRowIndexToModel(
                                    TablaDeducciones.getSelectedRow()
                            );

                            controller.removeDeduction(
                                    tm.getRowAt(row)
                            );
                        }
                    }
                }
        );
    }

    // =========================
    // GETTERS / SETTERS MVC
    // =========================

    public JPanel getPanel() {
        return panel;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;

        model.addPropertyChangeListener(this);
    }

    // =========================
    // CARGAR RUBROS
    // =========================

    public void loadRubros(
            List<Bonos> bonos,
            List<Deducciones> deducciones) {

        GradoMaestriacomboBox.setModel(
                new DefaultComboBoxModel<>(
                        bonos.toArray(new Bonos[0])
                )
        );

        SegurodevidacomboBox.setModel(
                new DefaultComboBoxModel<>(
                        deducciones.toArray(new Deducciones[0])
                )
        );

        if (GradoMaestriacomboBox.getItemCount() > 0) {
            GradoMaestriacomboBox.setSelectedIndex(0);
        }

        if (SegurodevidacomboBox.getItemCount() > 0) {
            SegurodevidacomboBox.setSelectedIndex(0);
        }
    }

    // =========================
    // TAKE
    // =========================

    private Empleado take() throws Exception {

        String cedula =
                CedulatextField.getText().trim();

        String nombre =
                NombretextField.getText().trim();

        String telefono =
                TelefonoTextField.getText().trim();

        String correo =
                CorreoTextField.getText().trim();

        double salario;

        try {
            salario = Double.parseDouble(
                    SalariotextField.getText().trim()
            );

        } catch (Exception e) {
            throw new Exception(
                    "El salario debe ser numérico"
            );
        }

        validateFields(
                cedula,
                nombre,
                telefono,
                correo,
                salario
        );

        Empleado empleado = new Empleado(
                cedula,
                nombre,
                telefono,
                correo,
                salario
        );

        empleado.setBonos(
                model.getBonuses()
        );

        empleado.setDeducciones(
                model.getDeductions()
        );

        return empleado;
    }

    // =========================
    // VALIDATE
    // =========================

    private void validateFields(
            String cedula,
            String nombre,
            String telefono,
            String correo,
            double salario) throws Exception {

        resetFieldColors();

        StringBuilder errors =
                new StringBuilder();

        if (cedula.isBlank()) {
            mark(CedulatextField);
            errors.append(
                    "Cédula requerida.\n"
            );
        }

        if (nombre.isBlank()) {
            mark(NombretextField);
            errors.append(
                    "Nombre requerido.\n"
            );
        }

        if (telefono.isBlank()) {
            mark(TelefonoTextField);
            errors.append(
                    "Teléfono requerido.\n"
            );
        }

        if (correo.isBlank()
                || !correo.contains("@")) {

            mark(CorreoTextField);

            errors.append(
                    "Correo inválido.\n"
            );
        }

        if (salario < 0) {
            mark(SalariotextField);

            errors.append(
                    "Salario inválido.\n"
            );
        }

        if (!errors.isEmpty()) {
            throw new Exception(
                    errors.toString()
            );
        }
    }

    // =========================
    // VALIDACIÓN VISUAL
    // =========================

    private void mark(JTextField field) {
        field.setBackground(
                new Color(255, 180, 180)
        );
    }

    private void resetFieldColors() {

        JTextField[] fields = {
                CedulatextField,
                NombretextField,
                TelefonoTextField,
                CorreoTextField,
                SalariotextField
        };

        for (JTextField field : fields) {
            field.setBackground(
                    UIManager.getColor(
                            "TextField.background"
                    )
            );
        }
    }

    // =========================
    // AGREGAR EMPLEADO
    // =========================

    private void addEmployee() {

        try {

            controller.addEmployee(
                    take()
            );

            JOptionPane.showMessageDialog(
                    panel,
                    "Empleado agregado correctamente"
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    panel,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // MODIFICAR EMPLEADO
    // =========================

    private void modifyEmployee() {

        try {

            if (model.getCurrentEmployee() == null
                    || model.getCurrentEmployee()
                    .getCedula()
                    .isBlank()) {

                throw new Exception(
                        "Seleccione un empleado de la tabla antes de modificar"
                );
            }

            controller.modifyEmployee(
                    take()
            );

            Tabla3.clearSelection();

            JOptionPane.showMessageDialog(
                    panel,
                    "Empleado modificado correctamente"
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    panel,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // PROPERTY CHANGE
    // =========================

    @Override
    public void propertyChange(
            PropertyChangeEvent evt) {

        switch (evt.getPropertyName()) {

            // -------------------------
            // EMPLEADO ACTUAL
            // -------------------------

            case Model.CURRENT_EMPLOYEE -> {

                Empleado empleado =
                        model.getCurrentEmployee();

                CedulatextField.setText(
                        empleado.getCedula()
                );

                NombretextField.setText(
                        empleado.getNombre()
                );

                TelefonoTextField.setText(
                        empleado.getTelefono()
                );

                CorreoTextField.setText(
                        empleado.getCorreo()
                );

                SalariotextField.setText(
                        empleado.getCedula().isBlank()
                                ? "0.0"
                                : String.valueOf(
                                empleado.getSalarioBase()
                        )
                );

                boolean editing =
                        !empleado.getCedula().isBlank();

                modificarButton.setEnabled(
                        editing
                );

                agregarButton2.setEnabled(
                        !editing
                );

                resetFieldColors();
            }

            // -------------------------
            // EMPLEADOS
            // -------------------------

            case Model.EMPLOYEES -> {

                Tabla3.setModel(
                        new EmpleadoTableModel(
                                new int[]{
                                        EmpleadoTableModel.CEDULA,
                                        EmpleadoTableModel.NOMBRE,
                                        EmpleadoTableModel.TELEFONO,
                                        EmpleadoTableModel.CORREO,
                                        EmpleadoTableModel.SALARIO_BASE,
                                        EmpleadoTableModel.SALARIO_BRUTO,
                                        EmpleadoTableModel.SALARIO_NETO
                                },
                                model.getEmployees()
                        )
                );
            }

            // -------------------------
            // BONOS
            // -------------------------

            case Model.BONUSES -> {

                Tabla2.setModel(
                        new BonoTableModel(
                                new int[]{
                                        BonoTableModel.NOMBRE,
                                        BonoTableModel.VALOR
                                },
                                model.getBonuses()
                        )
                );
            }

            // -------------------------
            // DEDUCCIONES
            // -------------------------

            case Model.DEDUCTIONS -> {

                TablaDeducciones.setModel(
                        new DeduccionTableModel(
                                new int[]{
                                        DeduccionTableModel.NOMBRE,
                                        DeduccionTableModel.VALOR
                                },
                                model.getDeductions()
                        )
                );
            }
        }

        panel.revalidate();
        panel.repaint();
    }

    // =========================
    // FALLBACK UI
    // =========================

    private void buildFallbackUI() {

        panel = new JPanel(
                new BorderLayout(8, 8)
        );

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        15,
                        15,
                        15
                )
        );

        JPanel editor = new JPanel(
                new GridLayout(1, 2, 10, 0)
        );

        editor.setBorder(
                BorderFactory.createTitledBorder(
                        "Empleado"
                )
        );

        // =========================
        // DATOS DEL EMPLEADO
        // =========================

        JPanel left =
                new JPanel(new GridBagLayout());

        GridBagConstraints c =
                new GridBagConstraints();

        c.insets =
                new Insets(3, 3, 3, 3);

        c.fill =
                GridBagConstraints.HORIZONTAL;

        c.weightx = 1;

        CedulatextField =
                new JTextField();

        NombretextField =
                new JTextField();

        TelefonoTextField =
                new JTextField();

        CorreoTextField =
                new JTextField();

        SalariotextField =
                new JTextField("0.0");

        JTextField[] fields = {
                CedulatextField,
                NombretextField,
                TelefonoTextField,
                CorreoTextField,
                SalariotextField
        };

        String[] labels = {
                "Cédula:",
                "Nombre:",
                "Teléfono:",
                "Correo:",
                "Salario:"
        };

        for (int i = 0; i < fields.length; i++) {

            c.gridx = 0;
            c.gridy = i;
            c.weightx = 0;

            left.add(
                    new JLabel(labels[i]),
                    c
            );

            c.gridx = 1;
            c.weightx = 1;

            left.add(
                    fields[i],
                    c
            );
        }

        // =========================
        // BOTONES
        // =========================

        JPanel buttons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );

        agregarButton2 =
                new JButton("Agregar");

        modificarButton =
                new JButton("Modificar");

        limpiarButton =
                new JButton("Limpiar");

        buttons.add(
                agregarButton2
        );

        buttons.add(
                modificarButton
        );

        buttons.add(
                limpiarButton
        );

        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;

        left.add(
                buttons,
                c
        );

        editor.add(left);

        // =========================
        // BONOS Y DEDUCCIONES
        // =========================

        JPanel right =
                new JPanel();

        right.setLayout(
                new BoxLayout(
                        right,
                        BoxLayout.Y_AXIS
                )
        );

        // BONOS

        JPanel bonusPanel =
                new JPanel(
                        new BorderLayout(5, 5)
                );

        bonusPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Bonos"
                )
        );

        GradoMaestriacomboBox =
                new JComboBox<>();

        agregarButton =
                new JButton("Agregar");

        JPanel bonusTop =
                new JPanel(
                        new BorderLayout(5, 0)
                );

        bonusTop.add(
                GradoMaestriacomboBox,
                BorderLayout.CENTER
        );

        bonusTop.add(
                agregarButton,
                BorderLayout.EAST
        );

        Tabla2 =
                new JTable();

        bonusPanel.add(
                bonusTop,
                BorderLayout.NORTH
        );

        bonusPanel.add(
                new JScrollPane(Tabla2),
                BorderLayout.CENTER
        );

        // DEDUCCIONES

        JPanel deductionPanel =
                new JPanel(
                        new BorderLayout(5, 5)
                );

        deductionPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Deducciones"
                )
        );

        SegurodevidacomboBox =
                new JComboBox<>();

        agregarButton1 =
                new JButton("Agregar");

        JPanel deductionTop =
                new JPanel(
                        new BorderLayout(5, 0)
                );

        deductionTop.add(
                SegurodevidacomboBox,
                BorderLayout.CENTER
        );

        deductionTop.add(
                agregarButton1,
                BorderLayout.EAST
        );

        TablaDeducciones =
                new JTable();

        deductionPanel.add(
                deductionTop,
                BorderLayout.NORTH
        );

        deductionPanel.add(
                new JScrollPane(
                        TablaDeducciones
                ),
                BorderLayout.CENTER
        );

        right.add(
                bonusPanel
        );

        right.add(
                deductionPanel
        );

        editor.add(right);

        panel.add(
                editor,
                BorderLayout.CENTER
        );

        // =========================
        // TABLA GENERAL EMPLEADOS
        // =========================

        Tabla3 =
                new JTable();

        JScrollPane all =
                new JScrollPane(Tabla3);

        all.setBorder(
                BorderFactory.createTitledBorder(
                        "Empleados"
                )
        );

        all.setPreferredSize(
                new Dimension(
                        900,
                        220
                )
        );

        panel.add(
                all,
                BorderLayout.SOUTH
        );
    }
}