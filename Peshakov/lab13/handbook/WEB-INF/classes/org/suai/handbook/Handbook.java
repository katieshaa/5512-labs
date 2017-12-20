package org.suai.handbook;

import java.util.*;
import java.io.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletConfig;

public class Handbook {
    private static class Record {
        public String name;
        public ArrayList<String> phones = new ArrayList<>();
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(String phone : phones) {
                sb.append(name);
                sb.append(' ');
                sb.append(phone);
                sb.append('\n');
            }
            return sb.toString();
        }
    }
    private ArrayList<Record> records;
    public Handbook() {
        records = new ArrayList<Record>();
    }

    public void add(String name, String phone) {
        int ind = find(name);
        if(ind != -1) {
            Record record = records.get(ind);
            if(record.phones.contains(phone)) {
                return;
            }
            else {
                records.get(ind).phones.add(phone);
            }
        }
        else {
            Record record = new Record();
            record.name = name;
            record.phones.add(phone);
            records.add(record);
        }
    }

    public void remove(String name) {
        int ind = find(name);
        if(ind == -1) {
            return;
        }
        records.remove(ind);
    }

    public void remove(String name, String phone) {
        int ind = find(name);
        if(ind == -1) {
            return;
        }
        records.get(ind).phones.remove(phone);
        if(records.get(ind).phones.size() == 0) {
            records.remove(ind);
        }

    }


    public String toXmlTable() {
        StringBuilder result = new StringBuilder();
        result.append("<table>");
        for(Record record : records) {
            result.append("<tr>");
            result.append("<td valign=\"top\">" + record.name + "</td>");
            StringBuilder sb = new StringBuilder();
            for(String phone : record.phones) {
                sb.append(phone);
                sb.append("<br>");
            }
            result.append("<td>" + sb.toString() + "</td>");
            result.append("</tr>");
        }
        result.append("</table>");
        return result.toString();
    }

    private int find(String name) {
        for(int i = 0; i < records.size(); ++i) {
            if(records.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Record record : records) {
            sb.append(record.toString());
        }
        return sb.toString();
    }
}