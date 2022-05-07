package com.example.bt_android_thuctap.adpter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.databinding.UserContainerBinding;
import com.example.bt_android_thuctap.model.User;

import java.util.List;

public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<User> data;

    public UserAdapter(List<User> data) {
        this.data = data;
        Log.e("TAG", "UserAdapter: ");
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserContainerBinding biding = UserContainerBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
        Log.e("haha", "onCreateViewHolder: kakakak" );
        return new UserViewHolder(biding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(data.get(position));
        Log.e("ad", "onBindViewHolder: "+position );

    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

//        private TextView txtName,txtChat;
//        private ImageView imgUser;
        UserContainerBinding biding;

//        public UserViewHolder(@NonNull View itemView) {
//            super(itemView);

//            txtName =  itemView.findViewById(R.id.txtNameUserContainer);
//            txtChat = itemView.findViewById(R.id.txtChatUserContainer);
//            imgUser = itemView.findViewById(id.img_usercontainer);
//            b
//        }

        public UserViewHolder(UserContainerBinding hbiding) {
            super(hbiding.getRoot());
            this.biding = hbiding;
        }
        public void setUserData(User user){
            Log.e("TAG", "setUserData: hahahah" );
            biding.imgUsercontainer.setImageResource(R.drawable.ic_lock);
            biding.txtNameUserContainer.setText(user.getName());
            biding.txtChatUserContainer.setText("hohoh");
            Log.e("TAG", "setUserData: hahahah" );
        }
    }
}
