package br.com.qrcred.convenio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import br.com.qrcred.R;
import br.com.qrcred.RecyclerAdapter;
import br.com.qrcred.conta;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContaConvenioAdapter extends RecyclerView.Adapter <ContaConvenioAdapter.MyViewHolder> {
    private final List<conta> elementos;
    public final List<conta> elementosFull;
    public RecycleViewClickListner listnerx;
    ImageView imgCupom;
    String uri_cupon;
    public final Context context;
    LayoutInflater inflater;
    //private final SelectedUser selectedUser;
    CircleImageView circleImageView;

    public ContaConvenioAdapter(Context context, List<conta> elementos,RecycleViewClickListner listnerx){
        this.elementos = elementos;
        this.context = context;
        this.elementosFull = new ArrayList<>(elementos);
        this.listnerx = listnerx;
        //this.selectedUser = selectedUser;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ContaConvenioAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.linhaconta,parent,false);
        return new ContaConvenioAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {

        String auxvalor;
        double dblvalor;
        conta mconta = elementos.get(position);
        String data_exibir;
        String uri_cupom;
        viewHolder.textViewAssociado.setText(mconta.getNome());
        data_exibir = mconta.getDia();
        String []data_exibir_correto = data_exibir.split("-");
        String data_formatada = data_exibir_correto[2] + "/" + data_exibir_correto[1] + "/" + data_exibir_correto[0];
        viewHolder.textViewData.setText(data_formatada);
        viewHolder.textViewHora.setText(mconta.getHora());
        viewHolder.textViewParcela.setText(mconta.getParcela());
        auxvalor = mconta.getValor();
        dblvalor = Double.parseDouble(auxvalor);
        auxvalor =  String.format("%.2f", dblvalor);
        viewHolder.textViewValor.setText(auxvalor);

        uri_cupom = mconta.getUri_cupom();

        uri_cupon = elementosFull.get(position).getUri_cupom();

        if (!uri_cupom.equals("")){

            Picasso.get()
                    .load(elementosFull.get(position).getUri_cupom())
                    .into(circleImageView);
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewAssociado,textViewData,textViewHora,textViewParcela,textViewValor;


        public MyViewHolder(@NonNull View view){
            super(view);
            textViewAssociado = view.findViewById(R.id.textViewAssociado);
            textViewData = view.findViewById(R.id.textViewData);
            textViewHora = view.findViewById(R.id.textViewHora);
            textViewParcela = view.findViewById(R.id.textViewParcela);
            textViewValor = view.findViewById(R.id.textViewValor);
            imgCupom = view.findViewById(R.id.iconeVerCupom);
            circleImageView   = view.findViewById(R.id.iconeVerCupom);
            // *********************** somente para imagem
           /* RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontTransform();
            if (uri_cupon != null){
                if (!uri_cupon.equals("")) {
                    Glide.with(circleImageView).load(uri_cupon).apply(options).override(90, 120).into(circleImageView);
                }
            }*/
            // *********************** somente para imagem
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listnerx.onClick(view, getAbsoluteAdapterPosition());
        }
    }
    public interface SelectedUser{
        void selectedUser(conta mConta);
    }


    @Override
    public int getItemCount() {
        return elementos.size();
    }
    public interface RecycleViewClickListner{
        void onClick(View v,int position);
    }
}


