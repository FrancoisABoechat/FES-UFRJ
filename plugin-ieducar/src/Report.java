import static net.sf.dynamicreports.report.builder.DynamicReports.*;


import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.*;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

import java.awt.*;
import java.math.BigDecimal;

public class Report {

    public Report(){ build(); }
    private void build() {
        try {
            StyleBuilder boldStyle = stl.style().bold();
            StyleBuilder boldCenteredStyle = stl.style(boldStyle)
                                                    .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
            StyleBuilder columnTitleStyle = stl.style(boldCenteredStyle)
                                                    .setBorder(stl.pen1Point())
                                                    .setBackgroundColor(Color.LIGHT_GRAY);

            TextColumnBuilder<String> nameColumn = col.column("Aluno", "aluno", type.stringType());
            TextColumnBuilder<Integer> classColumn = col.column("Turma", "turma", type.integerType());
            TextColumnBuilder<Integer> idColumn = col.column("Registro", "registro", type.integerType());
            TextColumnBuilder<BigDecimal> nota1Column = col.column("Nota 1", "nota1", type.bigDecimalType());
            TextColumnBuilder<BigDecimal> nota2Column = col.column("Nota 2", "nota2", type.bigDecimalType());
            TextColumnBuilder<BigDecimal> somaNotasColumn = nota1Column.add(nota2Column);
            TextColumnBuilder<BigDecimal> mediaFinalColumn = somaNotasColumn.divide(2,2.00)
                                                                               .setTitle("Média Final");
            report()
                    .columns( nameColumn, classColumn, idColumn , nota1Column, nota2Column, mediaFinalColumn)
                    .setColumnTitleStyle(columnTitleStyle)
                    .highlightDetailEvenRows()
                    .title(cmp.text("Teste de Relatório")).setTextStyle(boldCenteredStyle)
                    .pageFooter(cmp.pageXofY()).setTextStyle(boldCenteredStyle)
                    .setDataSource(createDataSource())
                    .show();

        } catch (DRException e) {
            e.printStackTrace();
        }
    }
    private JRDataSource createDataSource(){
        DRDataSource dataSource = new DRDataSource("aluno", "turma", "registro", "nota1", "nota2");
        dataSource.add("Aline de Freire Rezende", 201, 101010, new BigDecimal(10), new BigDecimal(9.5));
        dataSource.add("Larissa Galeno", 104, 256969, new BigDecimal(10), new BigDecimal(10));
        dataSource.add("Gilberto Lopes Pai", 303, 888888, new BigDecimal(3.2), new BigDecimal(6.4));
        dataSource.add("Gilberto Lopes Filho", 304, 555555, new BigDecimal(0), new BigDecimal(2.1));
        dataSource.add("Tomaz Cuber Guimarães", 95, 686868, new BigDecimal(7.5), new BigDecimal(8.25));
        return dataSource;
    }

    public static void main(String[] args){
        new Report();
    }
}

