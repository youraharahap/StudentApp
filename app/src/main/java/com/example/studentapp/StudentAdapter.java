package com.example.studentapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    List<StudentModel> student;
    Context context;
    DBHelper dbHelper;

    public StudentAdapter(List<StudentModel> student, Context context) {
        this.student = student;
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final StudentModel studentModel = student.get(position);
        holder.textViewID.setText(studentModel.getStudentId());
        holder.editTextName.setText(studentModel.getName());

        // Spinner (sama seperti FragmentSpinner di week 3)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                StudentModel.MAJORS
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinnerMajor.setAdapter(adapter);
        if (studentModel.getMajor().equals(StudentModel.MAJORS[1])) {
            holder.spinnerMajor.setSelection(1);
        } else {
            holder.spinnerMajor.setSelection(0);
        }

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName = holder.editTextName.getText().toString();
                String strMajor = holder.spinnerMajor.getSelectedItem().toString();

                if (strName.length() <= 0) {
                    Toast.makeText(context, "Name is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHelper.updateStudent(new StudentModel(studentModel.getStudentId(), strName, strMajor));
                Toast.makeText(context, "Edit Successful", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteStudent(studentModel.getStudentId());
                student.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return student.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewID;
        EditText editTextName;
        Spinner spinnerMajor;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewID = itemView.findViewById(R.id.textViewID);
            editTextName = itemView.findViewById(R.id.editTextName);
            spinnerMajor = itemView.findViewById(R.id.spinnerMajor);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}