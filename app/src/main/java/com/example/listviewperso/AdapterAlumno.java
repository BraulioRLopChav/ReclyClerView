package com.example.listviewperso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterAlumno extends RecyclerView.Adapter<AdapterAlumno.ViewHolder> implements View.OnClickListener, Filterable {

    protected ArrayList<Alumno> listaAlumnos;
    private ArrayList<Alumno> listaAlumnosFiltrados;
    private View.OnClickListener listener;
    private Context context;
    private LayoutInflater inflater;

    public AdapterAlumno(ArrayList<Alumno> listaAlumnos, Context context) {
        this.listaAlumnos = listaAlumnos;
        this.listaAlumnosFiltrados = new ArrayList<>(listaAlumnos);
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.alumno_items, null);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Alumno alumno = listaAlumnos.get(position);
        holder.txtMatricula.setText(alumno.getMatricula());
        holder.txtNombre.setText(alumno.getNombre());
        holder.txtCarrera.setText(alumno.getCarrera());
        holder.idImagen.setImageResource(alumno.getImg());
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) listener.onClick(v);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.values = listaAlumnosFiltrados;
                    filterResults.count = listaAlumnosFiltrados.size();
                } else {
                    String searchStr = constraint.toString().toLowerCase();
                    ArrayList<Alumno> alumnosFilter = new ArrayList<Alumno>();
                    for (Alumno alumno: listaAlumnosFiltrados){
                        if (alumno.getNombre().toLowerCase().contains(searchStr) || alumno.getMatricula().toLowerCase().contains(searchStr)|| alumno.getCarrera().toLowerCase().contains(searchStr)){
                            alumnosFilter.add(alumno);
                        }
                    }

                    filterResults.values = alumnosFilter;
                    filterResults.count = alumnosFilter.size();
                }
                 return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaAlumnos = (ArrayList<Alumno>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LayoutInflater inflater;
        private TextView txtNombre;
        private TextView txtMatricula;
        private TextView txtCarrera;
        private ImageView idImagen;

        public ViewHolder(@NonNull View view) {
            super(view);
            txtNombre = (TextView) view.findViewById(R.id.txtAlumnoNombre);
            txtMatricula = (TextView) view.findViewById(R.id.txtMatricula);
            txtCarrera = (TextView) view.findViewById(R.id.txtCarrera);
            idImagen = (ImageView) view.findViewById(R.id.foto);

        }
    }
}
