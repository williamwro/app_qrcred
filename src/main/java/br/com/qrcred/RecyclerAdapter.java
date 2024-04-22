package br.com.qrcred;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
   Context context;
   final private ArrayList<conta> data;
   LayoutInflater inflater;
   public RecycleViewClickListner listnerx;
   String uri_cupon;
   CircleImageView circleImageView;
   public RecyclerAdapter(Context context, ArrayList<conta> data,RecycleViewClickListner listnerx) {
      this.context = context;
      this.data = data;
      this.listnerx = listnerx;
      inflater = LayoutInflater.from(context);


   }

   @NonNull
   @Override
   public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      final View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
      return new RecyclerAdapter.MyViewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

      String auxvalor;
      double dblvalor;
      myViewHolder.textViewConveniox.setText(data.get(position).getRazaoSocial());
      String data_exibir;

      data_exibir = data.get(position).getDia();
      String []data_exibir_correto = data_exibir.split("-");
      String data_formatada = data_exibir_correto[2] + "/" + data_exibir_correto[1] + "/" + data_exibir_correto[0];

      myViewHolder.textViewDatax.setText(data_formatada);
      myViewHolder.textViewHorax.setText(data.get(position).getHora());
      myViewHolder.textViewParcelax.setText(data.get(position).getParcela());
      auxvalor = data.get(position).getValor();
      dblvalor = Double.parseDouble(auxvalor);
      auxvalor =  String.format("%.2f", dblvalor);
      myViewHolder.textViewValorx.setText(auxvalor);
      uri_cupon = data.get(position).getUri_cupom();
      if (!uri_cupon.equals("")){

         Picasso.get()
                 .load(data.get(position).getUri_cupom())
                 .into(circleImageView);
      }

   }

   public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

      TextView textViewConveniox,textViewDatax,textViewHorax,textViewParcelax,textViewValorx;

      public MyViewHolder(@NonNull View itemView) {
         super(itemView);
         circleImageView   = itemView.findViewById(R.id.iconeVerCupom);
         textViewConveniox = itemView.findViewById(R.id.textViewConvenio);
         textViewDatax     = itemView.findViewById(R.id.textViewData);
         textViewHorax     = itemView.findViewById(R.id.textViewHora);
         textViewParcelax  = itemView.findViewById(R.id.textViewParcela);
         textViewValorx    = itemView.findViewById(R.id.textViewValor);

         // *********************** somente para imagem
         /*RequestOptions options = new RequestOptions()
                 .centerCrop()
                 .placeholder(R.drawable.progress_animation)
                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                 .priority(Priority.HIGH)
                 .dontTransform();
         *//*if (!uri_cupon.equals("")){*//*
            Glide.with(circleImageView).load(uri_cupon).apply(options).override(90,120).into(circleImageView);*/
         //}
         // *********************** somente para imagem
         itemView.setOnClickListener(this);
      }

      @Override
      public void onClick(View itemView) {
         listnerx.onClick(itemView, getAbsoluteAdapterPosition());
      }
   }
   @Override
   public int getItemCount() {
      return data == null ? 0 : data.size();
   }

   public interface RecycleViewClickListner{
      void onClick(View v,int position);
   }
}
