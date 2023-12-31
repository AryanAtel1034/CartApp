package com.example.shoppingcart.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoppingcart.R;
import com.example.shoppingcart.adapters.CartListAdapter;
import com.example.shoppingcart.databinding.FragmentCartBinding;
import com.example.shoppingcart.models.CartItem;
import com.example.shoppingcart.viewmodels.ShopViewModel;

import java.util.List;

public class CartFragment extends Fragment implements CartListAdapter.CartInterface {

    private static final String TAG = "CartFragment";
    ShopViewModel shopViewModel;
    FragmentCartBinding fragmentCartBinding;
    NavController navController;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);
        return fragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        final CartListAdapter cartListAdapter = new CartListAdapter(this);
        fragmentCartBinding.cartRecyclerView.setAdapter(cartListAdapter);
        fragmentCartBinding.cartRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        shopViewModel = new ViewModelProvider(requireActivity()).get(ShopViewModel.class);
        shopViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartItem>>() {
            @Override
            public void onChanged(List<CartItem> cartItems) {
                cartListAdapter.submitList(cartItems);
                fragmentCartBinding.placeOrderButton.setEnabled(cartItems.size() > 0);
            }
        });
//
//        shopViewModel.getTotalQuantity().observe(getViewLifecycleOwner(), new Observer<Double>() {
//            @Override
//            public void onChanged(Double aDouble) {
//
//                if (aDouble==1 ){
//                    fragmentCartBinding.orderTotalText.setText("you get an Coke Free : ");
//
//                }
//            }
//        });

        shopViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {

                final double[] price = {aDouble};

                shopViewModel.getTotalQuantity().observe(getViewLifecycleOwner(), new Observer<Double>() {
                    @Override
                    public void onChanged(Double aDouble) {


                        if(price[0] >1000 && aDouble==1 ){
                            price[0] = price[0] - price[0] *0.2;

                            fragmentCartBinding.orderTotalText.setText("20% Discount Applied and you get a Coke free: ");
                            fragmentCartBinding.orderTotalTextView.setText("Total :  " + price[0]);
                        }

                         else if (price[0] >500 && price[0]<1000 && aDouble==1){
                            price[0] = price[0] - price[0] *0.1;
                            fragmentCartBinding.orderTotalText.setText("10% Discount Applied and you get a Coke free: ");
                            fragmentCartBinding.orderTotalTextView.setText("Total :  " +price[0]);
                        }
                        else if (price[0] >1000){
                            price[0] = price[0] - price[0] *0.2;

                            fragmentCartBinding.orderTotalText.setText("20% Discount Applied : ");
                            fragmentCartBinding.orderTotalTextView.setText("Total :  " +price[0]);

                        }
                        else if (price[0] >500 && price[0]<1000 ) {
                            price[0] = price[0] - price[0] *0.1;
                            fragmentCartBinding.orderTotalText.setText("10% Discount Applied : ");
                            fragmentCartBinding.orderTotalTextView.setText("Total :  " +price[0]);
                        }

                        else {

                           if (aDouble==1){
                               fragmentCartBinding.orderTotalText.setText("Only free Coke : ");
                               fragmentCartBinding.orderTotalTextView.setText("Total :  " + price[0]);

                           }
                           else{
                               fragmentCartBinding.orderTotalText.setText("No Discount Applicable : ");
                               fragmentCartBinding.orderTotalTextView.setText("Total :  " + price[0]);

                           }



                        }

                    }
                });



            }
        });


        fragmentCartBinding.placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_cartFragment_to_orderFragment);
            }
        });

    }

    @Override
    public void deleteItem(CartItem cartItem) {
        shopViewModel.removeItemFromCart(cartItem);
    }

    @Override
    public void changeQuantity(CartItem cartItem, int quantity) {
        shopViewModel.changeQuantity(cartItem, quantity);
    }
}