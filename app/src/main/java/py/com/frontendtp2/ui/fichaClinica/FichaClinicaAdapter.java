package py.com.frontendtp2.ui.fichaClinica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import py.com.frontendtp2.R;
import py.com.frontendtp2.data.model.FichaClinica;

public class FichaClinicaAdapter extends BaseAdapter implements Filterable {
    private List<FichaClinica> values;
    private List<FichaClinica> filteredValues;
    private Context context;
    ValueFilter valueFilter;

    public FichaClinicaAdapter(Context context, FichaClinica[] values){
        this.context = context;
        this.values = Arrays.asList(values);
        this.filteredValues = Arrays.asList(values);
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public FichaClinica getItem(int position) {
        return values.get(position);
    }

    public FichaClinica getItemAtPosition(int position) {return values.get(position);}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.fragment_ficha_clinica_item, parent, false);
        TextView idView = rowView.findViewById(R.id.fichaId);
        TextView empleadoView = rowView.findViewById(R.id.empleado);
        idView.setText(values.get(position).getIdFichaClinica().toString());
        String nombreE = "";
        if(values.get(position).getIdEmpleado().getNombre() != null){
            nombreE = values.get(position).getIdEmpleado().getNombre();
        }
        String apellidoE = "";
        if(values.get(position).getIdEmpleado().getApellido() != null){
            apellidoE = values.get(position).getIdEmpleado().getApellido();
        }
        empleadoView.setText(nombreE + "\n" + apellidoE);
        TextView clienteView = rowView.findViewById(R.id.cliente);
        String nombreC = "";
        if(values.get(position).getIdCliente().getNombre() != null){
            nombreC = values.get(position).getIdCliente().getNombre();
        }
        String apellidoC = "";
        if(values.get(position).getIdCliente().getApellido() != null){
            apellidoC = values.get(position).getIdCliente().getApellido();
        }
        clienteView.setText(nombreC + "\n" + apellidoC);
        TextView fechaDesdeView = rowView.findViewById(R.id.fechaItem);
        String text = values.get(position).getFechaHoraCadenaFormateada();
        String substring = text.substring(0, 10);
        fechaDesdeView.setText(substring);
        TextView tipoProdView = rowView.findViewById(R.id.TipoProducto);
        text = String.valueOf(values.get(position).getIdTipoProducto().getIdTipoProducto());
        tipoProdView.setText(text);

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
                List<FichaClinica> filterList = new ArrayList<>();
                for (int i = 0; i < filteredValues.size(); i++) {
                    if ((filteredValues.get(i).getIdCliente().getNombre().toUpperCase()).contains(constraint.toString().toUpperCase())) {
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
            values = (List<FichaClinica>) results.values;
            notifyDataSetChanged();
        }

    }

}
