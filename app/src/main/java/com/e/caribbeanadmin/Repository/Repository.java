package com.e.caribbeanadmin.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.e.caribbeanadmin.Listeners.OnCategoryLoadListeners;
import com.e.caribbeanadmin.Listeners.OnItemLoadListeners;
import com.e.caribbeanadmin.Listeners.OnMenuItemLoadListeners;
import com.e.caribbeanadmin.Listeners.OnShopLoadListeners;
import com.e.caribbeanadmin.Listeners.OnShopLocationLoadListeners;
import com.e.caribbeanadmin.dataModel.Item;
import com.e.caribbeanadmin.dataModel.MenuItem;
import com.e.caribbeanadmin.dataModel.Shop;
import com.e.caribbeanadmin.dataModel.ShopCategoryModel;
import com.e.caribbeanadmin.dataModel.ShopLocation;
import com.e.caribbeanadmin.dataModel.UserProfile;
import com.e.caribbeanadmin.DatabaseController.DatabaseAddresses;
import com.e.caribbeanadmin.Listeners.OnShopCategoryLoadListeners;
import com.e.caribbeanadmin.Listeners.OnUserProfileLoadListeners;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class Repository {
    private static final String TAG = "Repository";
    public static void getShopCategory(String categoryId, OnCategoryLoadListeners onCategoryLoadListeners){
        DatabaseAddresses.getShopCategoryCollection().document(categoryId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            onCategoryLoadListeners.onCategoriesLoaded(documentSnapshot.toObject(ShopCategoryModel.class));
                        }else{
                            onCategoryLoadListeners.onEmpty();
                            Log.d(TAG, "onSuccess: "+documentSnapshot.getReference().getPath());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onCategoryLoadListeners.onFailure(e.getMessage());
            }
        });
    }

    public static void getMyProfile(String userId, OnUserProfileLoadListeners onUserProfileLoadListeners){
            DatabaseAddresses.getUserAccountCollection(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    onUserProfileLoadListeners.onUserProfileLoaded(documentSnapshot.toObject(UserProfile.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onUserProfileLoadListeners.onFailure(e.getMessage());
                }
            });
        }

    public static void getAllShops(OnShopLoadListeners onShopLoadListeners){
        DatabaseAddresses.getShopCollection().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Shop> shopList=new ArrayList<>();

                for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots){
                    shopList.add(snapshot.toObject(Shop.class));
                }

                if(shopList.size()>0){
                    onShopLoadListeners.onShopsLoaded(shopList);
                }else{
                    onShopLoadListeners.onEmpty();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                onShopLoadListeners.onFailure(e.getMessage());
            }
        });
    }

         public static void getShopDealsAndPromotions(String itemShopId,OnItemLoadListeners onItemLoadListeners){
        DatabaseAddresses.getDealsCollection().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Item> items=new ArrayList<>();

                for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots){

                    Item item=snapshot.toObject(Item.class);
                    if(item.getShopId().equals(itemShopId)){
                        items.add(snapshot.toObject(Item.class));
                    }

                }

                if(items.size()>0){
                    onItemLoadListeners.onItemLoaded(items);
                }else{
                    onItemLoadListeners.onEmpty();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               onItemLoadListeners.onFailure(e.getMessage());
            }
        });
    }
         public static void getShopCategories(OnShopCategoryLoadListeners onShopCategoryLoadListeners){
            DatabaseAddresses.getShopCategoryCollection().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<ShopCategoryModel> shopCategoryModels=new ArrayList<>();
                    for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                        shopCategoryModels.add(documentSnapshot.toObject(ShopCategoryModel.class));
                    }

                    if(shopCategoryModels.size()>0){
                        onShopCategoryLoadListeners.onCategoriesLoaded(shopCategoryModels);
                    }else{
                        onShopCategoryLoadListeners.onEmpty();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onShopCategoryLoadListeners.onFailure(e.getMessage());
                }
            });
        }
        public static void getShopMenuCategories(String shopId, OnMenuItemLoadListeners onMenuItemLoadListeners){
            DatabaseAddresses.getShopMenuCollection().document(shopId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        onMenuItemLoadListeners.onMenuLoaded(documentSnapshot.toObject(MenuItem.class));
                    }else{
                        onMenuItemLoadListeners.onEmpty();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onMenuItemLoadListeners.onFailure(e.getMessage());
                }
            });

        }
        public static void getShopLocations(String shopId, OnShopLocationLoadListeners onShopLocationLoadListeners){
            DatabaseAddresses.getShopLocationCollection().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                    List<ShopLocation> shopLocations=new ArrayList<>();
                    for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                        ShopLocation location=documentSnapshot.toObject(ShopLocation.class);
                        if(location.getShopId().equals(shopId)){
                            shopLocations.add(location);

                        }
                    }

                    if(shopLocations.size()>0){
                        onShopLocationLoadListeners.onLocationsLoaded(shopLocations);
                    }else{
                        onShopLocationLoadListeners.onEmpty();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    onShopLocationLoadListeners.onFailure(e.getMessage());
                }
            });
        }
        public static void getShopStoreItem(String shopId,OnItemLoadListeners onItemLoadListeners){

            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    DatabaseAddresses.getShopStoreCollection().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<Item> items=new ArrayList<>();

                            for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                                Item item=documentSnapshot.toObject(Item.class);
                                if(item.getShopId().equals(shopId)){
                                    items.add(item);
                                }
                            }
                            if(items.size()>0){
                                onItemLoadListeners.onItemLoaded(items);
                            }else{
                                onItemLoadListeners.onEmpty();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            onItemLoadListeners.onFailure(e.getMessage());
                        }
                    });
                }
            });

        }

}
