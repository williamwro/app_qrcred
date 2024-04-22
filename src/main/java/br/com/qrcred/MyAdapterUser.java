package br.com.qrcred;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MyAdapterUser extends RecyclerView.Adapter<MyAdapterUser.MyViewHolderUser2> {

   Context context;
   List<Usuario_local> itens;
   public RecycleViewClickListner recycleViewClickListner_User;
   LayoutInflater inflater;

   public MyAdapterUser(Context context, List<Usuario_local> itens, RecycleViewClickListner recycleViewClickListner_User) {
      this.context = context;
      this.itens = itens;
      this.recycleViewClickListner_User = recycleViewClickListner_User;
      inflater = LayoutInflater.from(context);
   }

   @NonNull
   @Override
   public MyViewHolderUser2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      final View view = LayoutInflater.from(context).inflate(R.layout.item_view_user,parent,false);
      return new MyViewHolderUser2(view);
   }

   @Override
   public void onBindViewHolder(@NonNull MyViewHolderUser2 holder, int position) {
      holder.textViewUser.setText(itens.get(position).getNomeassociado());
      holder.textViewCartao.setText(itens.get(position).getCartao());
      holder.imageViewUsr.setImageResource (R.drawable.baseline_person_outline_24);
   }
   public class MyViewHolderUser2 extends RecyclerView.ViewHolder implements View.OnClickListener{

      TextView textViewUser,textViewCartao;
      ImageView imageViewUsr;

      public MyViewHolderUser2(@NonNull View itemView) {
         super(itemView);

         textViewUser   = itemView.findViewById(R.id.TvRotuloUsuario);
         textViewCartao = itemView.findViewById(R.id.tvCartao);
         imageViewUsr = itemView.findViewById(R.id.imageViewUser);

         itemView.setOnClickListener(this);
      }

      @Override
      public void onClick(View itemView) {
         recycleViewClickListner_User.onClick(itemView, getAbsoluteAdapterPosition());
      }
   }
   @Override
   public int getItemCount() {
      return itens.size();
   }
   public interface RecycleViewClickListner{
      void onClick(View v, int position);

   }
}
