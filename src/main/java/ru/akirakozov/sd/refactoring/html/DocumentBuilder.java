package ru.akirakozov.sd.refactoring.html;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

public class DocumentBuilder {
    private final static String EOLN = System.lineSeparator();

    List<String> content = new LinkedList<>();

    public void addLine(String line) {
        content.add(line);
    }

    public void addH1(String header) {
        content.add("<h1>" + header + "</h1>");
    }

    public void addLines(List<String> list) {
        content.addAll(list);
    }

    @Override
    public String toString() {
        StringJoiner s = new StringJoiner(EOLN, "<html><body>" + EOLN, EOLN + "</body></html>");
        content.forEach(s::add);
        return s.toString();
    }
}
