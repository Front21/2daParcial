package py.com.frontendtp2.ui.reserva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import py.com.frontendtp2.MainActivity;
import py.com.frontendtp2.R;
import py.com.frontendtp2.data.model.Reserva;

public class ReservaAdapter  extends BaseAdapter implements Filterable {

    private List<Reserva> values;
    private List<Reserva> filteredValues;
    private Context context;
    ValueFilter valueFilter;

    public ReservaAdapter(Context context, Reserva[] values){
        this.context = context;
        this.values = Arrays.asList(values);
        this.filteredValues = Arrays.asList(values);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Reserva getItem(int position) {
        return values.get(position);
    }

    public Reserva getItemAtPosition(int position) {return values.get(position);}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.fragment_reserva_item, parent, false);

        TextView idView = (TextView) rowView.findViewById(R.id.text_id2);
        TextView empleadoView = (TextView) rowView.findViewById(R.id.text_apellido2);
        TextView nombreView = (TextView) rowView.findViewById(R.id.text_nombre2);
        TextView fechaView = (TextView) rowView.findViewById(R.id.fechaItem);
        TextView estadoView = (TextView) rowView.findViewById(R.id.estadoItem);
        TextView asistenciaView = (TextView) rowView.findViewById(R.id.asistenciaItem);

        idView.setText(values.get(position).getIdReserva().toString());
        String nombreE = "";
        if(values.get(position).getIdEmpleado().getNombre() != null){
            nombreE = values.get(position).getIdEmpleado().getNombre();
        }
        String apellidoE = "";
        if(values.get(position).getIdEmpleado().getApellido() != null){
            apellidoE = values.get(position).getIdEmpleado().getApellido();
        }
        String text = nombreE+'\n'+apellidoE;
        empleadoView.setText(text);

        String nombreC = "";
        if(values.get(position).getIdCliente().getNombre() != null){
            nombreC = values.get(position).getIdCliente().getNombre();
        }
        String apellidoC = "";
        if(values.get(position).getIdCliente().getApellido() != null){
            apellidoC = values.get(position).getIdCliente().getApellido();
        }
        text = nombreC+'\n'+apellidoC;
        nombreView.setText(text);
        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yy");
        try {
            Date date = parseador.parse(values.get(position).getFecha());
            fechaView.setText(formateador.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        estadoView.setText(values.get(position).getFlagEstado());
        asistenciaView.setText(values.get(position).getFlagAsistio());

        return rowView;
    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<Reserva> filterList = new ArrayList<>();
                for (int i = 0; i < filteredValues.size(); i++) {
                    if ((filteredValues.get(i).getIdCliente().getNombreCompleto().toUpperCase()).contains(constraint.toString().toUpperCase())
                            ||(filteredValues.get(i).getIdEmpleado().getNombreCompleto().toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(filteredValues.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = filteredValues.size();
                results.values = filteredValues;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            values = (List<Reserva>) results.values;
            notifyDataSetChanged();
        }

    }
}