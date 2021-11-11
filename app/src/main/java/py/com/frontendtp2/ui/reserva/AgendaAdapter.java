package py.com.frontendtp2.ui.reserva;

import android.content.Intent;
import android.widget.ArrayAdapter;

import py.com.frontendtp2.data.model.Agenda;
import py.com.frontendtp2.data.model.Persona;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import py.com.frontendtp2.R;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import py.com.frontendtp2.data.model.FichaClinica;
import py.com.frontendtp2.data.model.Reserva;
import py.com.frontendtp2.ui.paciente.PacienteAdapter;

public class AgendaAdapter extends BaseAdapter implements Filterable {
    private List<Agenda> values;
    private List<Agenda> filteredValues;
    private Context context;
    ValueFilter valueFilter;

    public AgendaAdapter(Context context, Agenda[] values){
        this.context = context;
        this.values = Arrays.asList(values);
        this.filteredValues = Arrays.asList(values);
    }
    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Agenda getItem(int position) {
        return values.get(position);
    }

    public Agenda getItemAtPosition(int position) {return values.get(position);}

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.agenda_item, parent, false);
        TextView horaInicioView = rowView.findViewById(R.id.horaInicioItem);
        TextView horaFinView = rowView.findViewById(R.id.horaFinItem);
        TextView clienteView = rowView.findViewById(R.id.clienteItem);
        TextView fechaView = rowView.findViewById(R.id.fechaAgendaItem);
        //String text = values.get(position).getHoraInicio();
        //String hora = text.substring(0,1);
        //String minuto = text.substring(3,4);
        //text = hora+minuto;
        String text = values.get(position).getHoraInicioCadena()+'\n';
        horaInicioView.setText(text);
        //text = values.get(position).getHoraFin();
        //hora = text.substring(11,12);
        //minuto = text.substring(14,15);
        //text = hora+minuto;
        horaFinView.setText(values.get(position).getHoraFinCadena());
        if(values.get(position).getIdCliente() != null){
            clienteView.setText(values.get(position).getIdCliente().getNombre() + "\n" + values.get(position).getIdCliente().getApellido());
        }
        //text = values.get(position).getFecha();
        fechaView.setText(values.get(position).getFechaCadena());
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
                List<Agenda> filterList = new ArrayList<>();
                for (int i = 0; i < filteredValues.size(); i++) {
                    if ((filteredValues.get(i).getFechaCadena().toUpperCase()).contains(constraint.toString().toUpperCase())) {
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
            values = (List<Agenda>) results.values;
            notifyDataSetChanged();
        }

    }
}
