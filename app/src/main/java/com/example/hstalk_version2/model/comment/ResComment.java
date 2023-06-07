package com.example.hstalk_version2.model.comment;

import com.example.hstalk_version2.model.user.User;

import java.io.Serializable;
import java.util.ArrayList;

public class ResComment implements Serializable {
   private ArrayList<BaseComment> result;

   public ResComment(ArrayList<BaseComment> result) {
      this.result = result;
   }

   public ArrayList<BaseComment> getResult() {
      return result;
   }

   public void setResult(ArrayList<BaseComment> result) {
      this.result = result;
   }
}
