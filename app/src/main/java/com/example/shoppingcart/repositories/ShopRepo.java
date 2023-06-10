package com.example.shoppingcart.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingcart.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopRepo {

    private MutableLiveData<List<Product>> mutableProductList;

    public LiveData<List<Product>> getProducts() {
        if (mutableProductList == null) {
            mutableProductList = new MutableLiveData<>();
            loadProducts();
        }
        return mutableProductList;
    }

    private void loadProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(UUID.randomUUID().toString(), "Biryani", 1299, true, "https://i.pinimg.com/564x/f7/37/80/f7378044a24585b1394ae95247c4f442.jpg" ));
        productList.add(new Product(UUID.randomUUID().toString(), "Cake", 45, true, "https://i.pinimg.com/564x/95/68/35/9568353032fcf16a202d9256bef82f48.jpg"));
        productList.add(new Product(UUID.randomUUID().toString(), "Butter Chicken", 999, true, "https://i.pinimg.com/564x/2a/eb/18/2aeb18ebb3ff5c996bd2a2ae9f3f3d9c.jpg"));
        productList.add(new Product(UUID.randomUUID().toString(), "Salad", 699, false, "https://i.pinimg.com/564x/af/62/c2/af62c2e0cc9bc1349faf43d29234bcea.jpg"));
        productList.add(new Product(UUID.randomUUID().toString(), "Veg Pulao", 999, true, "https://i.pinimg.com/564x/d3/21/a8/d321a8356fe3796fc0724b7c5936b3f1.jpg"));
        productList.add(new Product(UUID.randomUUID().toString(), "Egg Curry", 1099, true, "https://i.pinimg.com/564x/b5/3a/45/b53a455c785eaf5e7084caaca6e0270a.jpg"));
        productList.add(new Product(UUID.randomUUID().toString(), "Rice", 399, true, "https://i.pinimg.com/564x/18/60/4c/18604cbbd8afcdb92239853c06b78172.jpg"));
        mutableProductList.setValue(productList);
    }
}
