package com.gmail.egorovsonalexey;

import javax.ejb.Local;
import java.util.ArrayList;

@Local
public interface MyLocalService {

    Tree<String> getFileTree();
}
