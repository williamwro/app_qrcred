package br.com.qrcred;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final ArrayList<Categorias> categoriasList;
    private final ArrayList<Categorias> tempArray;
    private ArrayList<Convenios_categorias> conveniosList;


    public ExpandableAdapter(Context context, ArrayList<Categorias> categoriasList) {
        this.context = context;
        this.categoriasList = new ArrayList<>();
        this.categoriasList.addAll(categoriasList);
        this.tempArray = new ArrayList<>();
        this.tempArray.addAll(categoriasList);
    }

    @Override
    public int getGroupCount() {
        return categoriasList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<Convenios_categorias> conveniosList = categoriasList.get(groupPosition).getConvenios();
        return conveniosList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return categoriasList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Convenios_categorias> conveniosList = categoriasList.get(groupPosition).getConvenios();
        return conveniosList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //String headerTitle = (String)getGroup(groupPosition);
        ViewHolderGroup holder;

        Categorias categorias = (Categorias) getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.header_espandable_list_view,null);
            holder = new ViewHolderGroup();
            convertView.setTag(holder);

            holder.tvGroup = convertView.findViewById(R.id.tvGroup);
        }else{

            holder = (ViewHolderGroup) convertView.getTag();

        }
        holder.tvGroup.setTypeface(null, Typeface.BOLD);
        holder.tvGroup.setText(categorias.getNome().trim());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //String headerTitle = (String)getGroup(groupPosition);
        ViewHolderItem holder;
        String Nome;
        String Endereco;
        String Bairro;
        String Numero;
        //String val = (String) getChild(groupPosition, childPosition).toString();
        Convenios_categorias convenios_categorias = (Convenios_categorias) getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_expandable_listview,null);
            holder = new ViewHolderItem();
            convertView.setTag(holder);

            holder.tvItem = convertView.findViewById(R.id.tvNomeFantasia);
            holder.tvEndereco = convertView.findViewById(R.id.tvEndereco);
            holder.tvBairro = convertView.findViewById(R.id.tvBairro);
        }else{
            holder = (ViewHolderItem) convertView.getTag();
        }
        holder.tvItem.setTypeface(null, Typeface.BOLD);
        Nome = convenios_categorias.getNOMEFANTASIA();
        Endereco = convenios_categorias.getENDERECO();
        Bairro = convenios_categorias.getBAIRRO();
        Numero = convenios_categorias.getNUMERO();

        Endereco = Endereco+ ", " +Numero;

        holder.tvItem.setText(Nome);
        holder.tvEndereco.setText(Endereco);
        holder.tvBairro.setText(Bairro);

        //TextView tvGroup = (TextView)convertView.findViewById(R.id.tvGroup);
        //holder.tvGroup.setTypeface(null, Typeface.BOLD);
        //holder.tvGroup.setText(headerTitle);
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void FilterData(String query)
    {
            Log.v("ListAdapter", String.valueOf(categoriasList.size()));
            categoriasList.clear();

            if(query != null && query.length()>0){
                query = query.toUpperCase();


                categoriasList.clear();

                ArrayList<String> filters = new ArrayList<>();

                for (Categorias categorias: tempArray) {

                    ArrayList<Convenios_categorias> conveniosList = categorias.getConvenios();
                    ArrayList<Convenios_categorias> newList = new ArrayList<>();

                    for (Convenios_categorias convenios_categorias : conveniosList) {
                        if (convenios_categorias.getNOMEFANTASIA().toUpperCase().contains(query)){
                            newList.add(convenios_categorias);
                        }
                    }
                    if (newList.size() > 0){
                        Categorias nCategorias = new Categorias(categorias.getNome(),newList);
                        categoriasList.add(nCategorias);
                        notifyDataSetChanged();
                    }else{
                        notifyDataSetInvalidated();
                    }
                }
            }else{
                categoriasList.addAll(tempArray);
            }
    }
}
class ViewHolderGroup{
    TextView tvGroup;
}
class ViewHolderItem{
    TextView tvItem, tvEndereco, tvBairro;
}
