package com.gmail.egorovsonalexey;

import javax.ejb.Local;

@Local
public interface MyLocalService {

    Tree<String> getFileTree();
}
