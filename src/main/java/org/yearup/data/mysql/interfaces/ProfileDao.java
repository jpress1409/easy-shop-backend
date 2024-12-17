package org.yearup.data.mysql.interfaces;


import org.yearup.models.Profile;

public interface ProfileDao
{
    Profile create(Profile profile);

    Profile getById(int userId);
}
