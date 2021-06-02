package mainPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.*;


@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    // Константы с исходным размером окна приложения
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    // Объект диалогового окна для выбора файлов.
    // Компонент не создается изначально, т.к. может и не понадобиться
    // пользователю если тот не собирается сохранять данные в файл
    private JFileChooser fileChooser = null;

    // Элементы меню вынесены в поля данных класса, так как ими необходимо
    // манипулировать из разных мест
    private JMenuItem saveToTextMenuItem;
    private JMenuItem searchValueMenuItem;
    // Поля ввода для считывания значений переменных
    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;
    private Box hBoxResult; //
    // Визуализатор ячеек таблицы
    private FunctionTableCellRenderer renderer = new FunctionTableCellRenderer();
    private mainPackage.FunctionTableModel data;  // модель данных с результами вычислений

    public MainFrame() {
        super("Табулирование функции на отрезке");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        JMenu tableMenu = new JMenu("Таблица");
        menuBar.add(tableMenu);
        JMenu ReferenceMenu = new JMenu("Справка");
        menuBar.add(ReferenceMenu);

        Action saveToTextAction = new AbstractAction(
                "Сохранить в текстовый файл") {
            public void actionPerformed(ActionEvent event) {
                // Если экземпляр диалогового окна "Открыть файл" еще не создан,
                // то создать его и инициализировать текущей директорией
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }

                // Показать диалоговое окно
                if (fileChooser.showSaveDialog(MainFrame.this) ==
                        JFileChooser.APPROVE_OPTION)
                    saveToTextFile(fileChooser.getSelectedFile());
            }
        };
        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        saveToTextMenuItem.setEnabled(false);

        // Создать новое действие по поиску значений функции
        Action searchValueAction = new AbstractAction("Найти значение функции") {
            public void actionPerformed(ActionEvent event) {
                // Запросить пользователя ввести искомую строку
                String value = JOptionPane.showInputDialog(MainFrame.this,
                        "Введите значение для поиска", "Поиск значения",
                        JOptionPane.QUESTION_MESSAGE);
                // Установить введенное значение в качестве иголки
                renderer.setNeedle(value);
                // Обновить таблицу
                getContentPane().repaint();
            }
        };
        searchValueMenuItem = tableMenu.add(searchValueAction);
        searchValueMenuItem.setEnabled(false);


        Action aboutProgrammAction = new AbstractAction("О программе") {
            public void actionPerformed(ActionEvent event) {
                JLabel info = new JLabel("Автор программы: Cтехнович Михаил Александровчи, 2 курс 4 группа");
                JOptionPane.showMessageDialog(MainFrame.this, info,
                        "О программе", JOptionPane.PLAIN_MESSAGE);
            }
        };
        ReferenceMenu.add(aboutProgrammAction);

        textFieldFrom = new JTextField("0.0", 10);
        textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize());

        textFieldTo = new JTextField("1.0", 10);
        textFieldTo.setMaximumSize(textFieldTo.getPreferredSize());

        textFieldStep = new JTextField("0.1", 10);
        textFieldStep.setMaximumSize(textFieldStep.getPreferredSize());

        Box hboxXRange = Box.createHorizontalBox();
        hboxXRange.add(Box.createHorizontalGlue());
        hboxXRange.add(new JLabel("X начальное:"));
        hboxXRange.add(Box.createHorizontalStrut(10));
        hboxXRange.add(textFieldFrom);
        hboxXRange.add(Box.createHorizontalStrut(20));
        hboxXRange.add(new JLabel("X конечное:"));
        hboxXRange.add(Box.createHorizontalStrut(10));
        hboxXRange.add(textFieldTo);
        hboxXRange.add(Box.createHorizontalStrut(20));
        hboxXRange.add(new JLabel("Шаг для X:"));
        hboxXRange.add(Box.createHorizontalStrut(10));
        hboxXRange.add(textFieldStep);
        hboxXRange.add(Box.createHorizontalGlue());

        Box Formula = Box.createHorizontalBox();
        Formula.add(Box.createHorizontalGlue());
        JLabel F = new JLabel("Делитель параболы:  Y = (X^2) / P  ");
        Formula.add(F);
        Formula.add(Box.createHorizontalGlue());

        JTextField textFieldParam = new JTextField("0.0", 10);
        textFieldParam.setMaximumSize(textFieldStep.getPreferredSize());

        Box Param = Box.createHorizontalBox();
        Param.add(Box.createHorizontalGlue());
        Param.add(new JLabel("P: "));
        Param.add(Box.createHorizontalStrut(10));
        Param.add(textFieldParam);
        Param.add(Box.createHorizontalGlue());


        Box Nast = Box.createVerticalBox();
        Nast.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Настройки:"));
        Nast.add(Box.createVerticalGlue());
        Nast.add(hboxXRange);
        Nast.add(Box.createHorizontalStrut(20));
        Nast.add(Formula);
        Nast.add(Box.createHorizontalStrut(20));
        Nast.add(Param);
        Nast.add(Box.createVerticalGlue());

        Nast.setPreferredSize(new Dimension(
                new Double(Nast.getMaximumSize().getWidth()).intValue(),
                new Double(Nast.getMinimumSize().getHeight()*1.5).intValue()));
        getContentPane().add(Nast, BorderLayout.NORTH);

        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    Double from = Double.parseDouble(textFieldFrom.getText());
                    Double to = Double.parseDouble(textFieldTo.getText());
                    Double step = Double.parseDouble(textFieldStep.getText());
                    Double p = Double.parseDouble(textFieldParam.getText());
                    data = new mainPackage.FunctionTableModel(from, to, step, p);
                    JTable table = new JTable(data);
                    table.setDefaultRenderer(Double.class, renderer);
                    table.setRowHeight(30);
                    hBoxResult.removeAll();
                    hBoxResult.add(new JScrollPane(table));
                    hBoxResult.revalidate();
                    saveToTextMenuItem.setEnabled(true);
                    searchValueMenuItem.setEnabled(true);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой",
                            "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JButton buttonReset = new JButton("Очистить поля");
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldFrom.setText("0.0");
                textFieldTo.setText("1.0");
                textFieldStep.setText("0.1");
                hBoxResult.removeAll();
                hBoxResult.repaint();
                saveToTextMenuItem.setEnabled(false);
                searchValueMenuItem.setEnabled(false);
            }
        });

        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.setBorder(BorderFactory.createEtchedBorder());
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.setPreferredSize(new Dimension(
                new Double(hboxButtons.getMaximumSize().getWidth()).intValue(),
                new Double(hboxButtons.getMinimumSize().getHeight()*1.5).intValue()));
        getContentPane().add(hboxButtons, BorderLayout.SOUTH);

        hBoxResult = Box.createHorizontalBox();
        getContentPane().add(hBoxResult);
    }

    protected void saveToTextFile(File selectedFile) {
        try {
            PrintStream out = new PrintStream(selectedFile);

            out.println("Результаты табулирования функции:");
            out.println("");
            out.println("Интервал от " + data.getFrom() + " до " + data.getTo() +
                    " с шагом " + data.getStep());
            out.println("====================================================");

            for (int i = 0; i<data.getRowCount(); i++)
                out.println("Значение в точке " + data.getValueAt(i,0)  + " равно " +
                        data.getValueAt(i,1));

            out.close();
        } catch (FileNotFoundException e) {
        }
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
