package com.ardysyahputra.imagemachine.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardysyahputra.imagemachine.Activity.Detail_MachineData;
import com.ardysyahputra.imagemachine.Activity.EditMachineData;
import com.ardysyahputra.imagemachine.Activity.MachineData;
import com.ardysyahputra.imagemachine.Database.DatabaseHelper;
import com.ardysyahputra.imagemachine.Model.Machine;
import com.ardysyahputra.imagemachine.R;

import java.util.List;

public class MachineAdapter extends RecyclerView.Adapter <MachineAdapter.ViewHolder> {

    private Context context;
    private List<Machine> MachineList;
    String[] listChoice;
    public MachineAdapter(Context context, List<Machine> Machine)
    {
        this.context = context;
        this.MachineList = Machine;
    }

    public MachineAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MachineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_machine_layout,parent,false);

        return new MachineAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MachineAdapter.ViewHolder holder, int position) {
        final Machine machine = MachineList.get(position);
        String id = machine.getId();
        holder.name.setText(machine.getName());
        holder.type.setText(machine.getType());
        listChoice = context.getResources().getStringArray(R.array.options);
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                mBuilder.setSingleChoiceItems(listChoice, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //  mResult.setText(listSort[i]);
                        if (i==0)
                        {
                            Intent intent = new Intent(context, EditMachineData.class);
                            intent.putExtra("id",machine.getId());
                            intent.putExtra("name",machine.getName());
                            intent.putExtra("type",machine.getType());
                            intent.putExtra("number",machine.getQR_Code_Number());
                            intent.putExtra("last",machine.getLast_Maintenance_Date());
                            Log.d("tes","id"+id);

                            context.startActivity(intent);
                        }
                        else
                        {
                            showAlert(id);
                        }
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detail_MachineData.class);
                intent.putExtra("id",machine.getId());
                intent.putExtra("name",machine.getName());
                intent.putExtra("type",machine.getType());
                intent.putExtra("number",machine.getQR_Code_Number());
                intent.putExtra("last",machine.getLast_Maintenance_Date());


                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MachineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name,type;
        ImageButton more;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.machineName);
            type = itemView.findViewById(R.id.machineType);
            more = itemView.findViewById(R.id.moreBtn);



        }
    }

    public void showAlert(String id)
    {
        new AlertDialog.Builder(context)
                .setTitle("Delete data")
                .setMessage("Are you sure you want to delete this data?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        databaseHelper.deleteData(id);
                        Toast.makeText(context, "Data Deleted", Toast.LENGTH_SHORT).show();
                        if (context instanceof MachineData) {
                            ((MachineData)context).getData();
                        }
                    }
                })


                .setNeutralButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.ic_warning_red_24dp)
                .show();
    }

}
