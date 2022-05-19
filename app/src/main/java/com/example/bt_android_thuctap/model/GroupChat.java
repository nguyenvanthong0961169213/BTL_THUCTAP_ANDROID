package com.example.bt_android_thuctap.model;

public class GroupChat {
    private String GroupName;
    private String GroupId;
    private String GroupMember;
    private String image;

    public GroupChat() {
    }

    public GroupChat(String groupName, String groupId, String groupMember, String image) {
        GroupName = groupName;
        GroupId = groupId;
        GroupMember = groupMember;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getGroupMember() {
        return GroupMember;
    }

    public void setGroupMember(String groupMember) {
        GroupMember = groupMember;
    }
}
