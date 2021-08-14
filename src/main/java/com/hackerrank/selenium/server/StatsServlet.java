package com.hackerrank.selenium.server;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class StatsServlet extends HttpServlet {
    private static Integer seed;
    private static String rand;
    private static List<String> doc;

    private static String head = "<head>\n" +
            "    <title>Covid-19 Stats</title>\n" +
            "    <link href=\"https://cdn.jsdelivr.net/npm/h8k-design@latest/dist/index.css\" rel=\"stylesheet\"/>\n" +
            "    <script src=\"https://cdn.jsdelivr.net/npm/h8k-components/dist/h8k-components/h8k-components.esm.js\"\n" +
            "            type=\"module\"></script>\n" +
            "    <script nomodule src=\"https://cdn.jsdelivr.net/npm/h8k-components/dist/h8k-components/h8k-components.js\"></script>\n" +
            "</head>";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "private, no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setCharacterEncoding("UTF-8");

        if (request.getParameter("seed") != null) {
            seed = Integer.parseInt(request.getParameter("seed"));
            rand = request.getParameter("rand");
            doc = IOUtils.readLines(StatsServlet.class.getResourceAsStream("/country_continent.txt"), StandardCharsets.UTF_8);
            doc.add(rand);

            response.getWriter().write("Done");
        } else {
            StringBuilder builder = new StringBuilder();
            builder.append("<html>\n");
            builder.append("<body>\n");
            builder.append(head);
            builder.append("\n<table>\n");

            builder.append("<thead>\n");
            builder.append("<tr>");

            builder.append("<th>Country");
            builder.append("</th>");

            builder.append("<th>Total Cases");
            builder.append("</th>");

            builder.append("<th>Total Deaths");
            builder.append("</th>");

            builder.append("<th>Total Recovered");
            builder.append("</th>");

            builder.append("<th>Continent");
            builder.append("</th>");

            builder.append("</tr>");
            builder.append("\n</thead>\n");

            builder.append("<tbody>");

            for (String key : doc) {
                String[] parts = key.split("=");

                builder.append("\n<tr>");

                builder.append("<td>" + parts[0]);
                builder.append("</td>");

                builder.append("<td>" + seed + "," + 700);
                builder.append("</td>");

                builder.append("<td>" + seed);
                builder.append("</td>");

                builder.append("<td>" + seed + "," + 100);
                builder.append("</td>");

                builder.append("<td>" + parts[1]);
                builder.append("</td>");

                builder.append("</tr>");
            }
            builder.append("\n</tbody>\n");

            builder.append("</table>\n</body>\n</html>");

            response.getWriter().write(builder.toString());
        }
    }
}
