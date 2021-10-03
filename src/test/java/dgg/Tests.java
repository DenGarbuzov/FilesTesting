package dgg;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import net.lingala.zip4j.core.ZipFile;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Test;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

public class Tests {
    @Test
    void someFileTest() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("build.txt")) {
            assert stream != null;
            String result = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            assertThat(result).contains("org.aspectj:aspectjweaver:1.9.6",
                    "com.codeborne:selenide:$selenideVersion",
                    "io.qameta.allure:allure-selenide:$allureVersion",
                    "org.assertj:assertj-core:3.19.0",
                    "org.junit.jupiter:junit-jupiter:$junitVersion");
        }
    }

    @Test
    void pdfTest() throws Exception {

        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("MadeForTestDPF.pdf")) {
            assert stream != null;
            PDF parsed = new PDF(stream);
            assertThat(parsed.numberOfPages).isEqualTo(1);
            assertThat(parsed.text).contains("Try to find some text!", "Linux Mint");
            System.out.println("All asserts are true");
        }
    }

    @Test
    void testingExcel() throws Exception {

        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("TestingEXCEL_1.xlsx")) {
            assert stream != null;
            XLS parsed = new XLS(stream);
            assertThat(parsed.excel.getSheetAt(0).getRow(4).getCell(1).getStringCellValue())
                    .isEqualTo("export ANDROID_SDK_ROOT=$HOME/Android/Sdk/tools");
            assertThat(parsed.excel.getSheetAt(0).getRow(1).getCell(2).getStringCellValue())
                    .isEqualTo("TRUE");
            System.out.println("All asserts are true");
        }
    }

    @Test
    void testingExcel2() throws Exception {

        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("TestingEXCEL_2.xls")) {
            assert stream != null;
            XLS parsed = new XLS(stream);
            assertThat(parsed.excel.getSheetAt(0).getRow(5).getCell(1).getStringCellValue())
                    .isEqualTo("export JAVA_HOME=$HOME/jre1.8.0_291");
            assertThat(parsed.excel.getSheetAt(0).getRow(0).getCell(2).getStringCellValue())
                    .isEqualTo("Staus");
            System.out.println("All asserts are true");
        }
    }

    @Test
    @Deprecated
    void parseZipFileTest() throws Exception {
        ZipFile zipFile = new ZipFile("./src/test/resources/buildZIP.zip");

            if (zipFile.isEncrypted()) {
                zipFile.setPassword("123".toCharArray());
                zipFile.extractAll("./src/test/resources/");
            }

        String result;
        try (FileInputStream stream = new FileInputStream("./src/test/resources/caller.txt")) {
            result = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        }
        assertThat(result).contains("call back later");
    }

    @Test
    void docxTest() throws Exception {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("NDA.docx")) {
            StringBuilder resultText = new StringBuilder();
            assert stream != null;
            XWPFDocument document = new XWPFDocument(stream);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph data : paragraphs) {
                resultText.append(data.getText());
                System.out.println(resultText);
            }
            assertThat(resultText.toString())
                    .contains("СОГЛАШЕНИЕ О НЕРАЗГЛАШЕНИИ ИНФОРМАЦИИ");
        }
    }
}


