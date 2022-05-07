package com.example.bt_android_thuctap.adpter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_android_thuctap.FragmentSceneChat;
import com.example.bt_android_thuctap.R;
import com.example.bt_android_thuctap.databinding.UserContainerBinding;
import com.example.bt_android_thuctap.fragmenthomeapp.Fragment_Home;
import com.example.bt_android_thuctap.model.User;

import java.util.List;

public class UserAdapter  extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<User> data;
    private Fragment_Home fragment_Home ;

    public UserAdapter(List<User> data , Fragment_Home fragmentHome) {
        this.data = data;
        fragment_Home = fragmentHome;
        Log.e("TAG", "UserAdapter: ");
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserContainerBinding binding = UserContainerBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,false);
        Log.e("haha", "onCreateViewHolder: kakakak" );
        return new UserViewHolder(binding);
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
        UserContainerBinding binding;

//        public UserViewHolder(@NonNull View itemView) {
//            super(itemView);

//            txtName =  itemView.findViewById(R.id.txtNameUserContainer);
//            txtChat = itemView.findViewById(R.id.txtChatUserContainer);
//            imgUser = itemView.findViewById(id.img_usercontainer);
//            b
//        }

        public UserViewHolder(UserContainerBinding hbinding) {
            super(hbinding.getRoot());
            this.binding = hbinding;
        }
        public void setUserData(User user){
            Log.e("TAG", "setUserData: hahahah" );
            binding.imgUsercontainer.setImageResource(R.drawable.ic_lock);
            binding.txtNameUserContainer.setText(user.getName());
            binding.txtChatUserContainer.setText("hohoh");
            binding.ctFriendUserContainer.setOnClickListener(v -> ClickChatFriend() );
            Log.e("TAG", "setUserData: hahahah" );
        }

        private void ClickChatFriend() {
            fragment_Home.LoadingFriend();

        }
    }
}
