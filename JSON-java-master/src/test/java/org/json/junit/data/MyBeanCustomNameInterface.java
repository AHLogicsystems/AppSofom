package org.json.junit.data;

import org.JsonAppSofom.JSONPropertyIgnore;
import org.JsonAppSofom.JSONPropertyName;

public interface MyBeanCustomNameInterface {
    @JSONPropertyName("InterfaceField")
    float getSomeFloat();
    @JSONPropertyIgnore
    int getIgnoredInt();
}