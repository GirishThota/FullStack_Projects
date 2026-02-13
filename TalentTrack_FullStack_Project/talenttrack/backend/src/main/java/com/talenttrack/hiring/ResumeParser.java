package com.talenttrack.hiring;

import java.io.InputStream;
import java.util.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

@Component
public class ResumeParser {

  private static final List<String> SKILL_KEYWORDS = List.of(
      "java","spring","spring boot","hibernate","jpa","sql","mysql","postgres",
      "mongodb","rest","microservices","docker","kubernetes","aws","azure",
      "javascript","typescript","angular","react","html","css","git","linux"
  );

  public String extractSkillsFromPdf(InputStream pdfStream) {
    try (PDDocument doc = PDDocument.load(pdfStream)) {
      String text = new PDFTextStripper().getText(doc).toLowerCase(Locale.ROOT);
      Set<String> found = new LinkedHashSet<>();
      for (String kw : SKILL_KEYWORDS) {
        if (text.contains(kw)) found.add(kw);
      }
      return String.join(", ", found);
    } catch (Exception e) {
      return "";
    }
  }
}
