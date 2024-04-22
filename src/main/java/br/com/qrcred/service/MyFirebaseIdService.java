package br.com.qrcred.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.jetbrains.annotations.NotNull;

class MyFirebaseIdService extends FirebaseMessagingService {

   @Override
   public void onNewToken(@NotNull String s)
   {
      super.onNewToken(s);
      FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
      String refreshToken= FirebaseMessaging.getInstance().getToken().toString();
      if(firebaseUser!=null){
         updateToken(refreshToken);
      }
   }
   private void updateToken(String refreshToken){
      FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
      Token token1 = new Token(refreshToken);
      FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
   }
}
