package com.example.android.asynctask;

public class MySensor {

   public int m_temperature;
   int m_humidity;
   int m_activity;
   int m_count;

   public MySensor(int temp, int humid, int act, int c)
   {
       m_temperature = temp;
       m_humidity = humid;
       m_activity = act;
       m_count = c;
   }

}
