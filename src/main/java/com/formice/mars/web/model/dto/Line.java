package com.formice.mars.web.model.dto;

public class Line {

   private String lineId;
   private String fromId;
   private String toId;

   public String getLineId() {
       return lineId;
   }

   public void setLineId(String lineId) {
       this.lineId = lineId;
   }

   public String getFromId() {
       return fromId;
   }

   public void setFromId(String fromId) {
       this.fromId = fromId;
   }

   public String getToId() {
       return toId;
   }

   public void setToId(String toId) {
       this.toId = toId;
   }
}
