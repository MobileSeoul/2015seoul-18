package com.eatinghabit.sehyunpark.eatinghabits.domain;

import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

/**
 * Created by Administrator on 2015-08-01.
 */
public class Person{
    private ProfileDrawerItem profile;
    private int background;

    public ProfileDrawerItem getProfile() { return profile; }

    public void setProfile(ProfileDrawerItem profile) { this.profile = profile; }

    public int getBackground() { return background; }

    public void setBackground(int background) { this.background = background; }
}
