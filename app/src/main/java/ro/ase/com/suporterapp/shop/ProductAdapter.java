package ro.ase.com.suporterapp.shop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ro.ase.com.suporterapp.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final List<Product> products;
    private final LayoutInflater inflater;
    private final OnAddToCartClickListener onAddToCartClickListener;
    private final OnRemoveFromCartClickListener onRemoveFromCartClickListener;

    public interface OnAddToCartClickListener {
        void onAddToCartClicked(Product product);
    }

    public interface OnRemoveFromCartClickListener {
        void onRemoveFromCartClicked(Product product);
    }

    // Constructor
    public ProductAdapter(Context context, List<Product> products,
                          OnAddToCartClickListener addListener,
                          OnRemoveFromCartClickListener removeListener) {
        this.inflater = LayoutInflater.from(context);
        this.products = products;
        this.onAddToCartClickListener = addListener;
        this.onRemoveFromCartClickListener = removeListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.shop_product_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView productNameTextView;
        private final TextView productDescriptionTextView;
        private final TextView productPriceTextView;
        private final ImageView productImageView;
        private final Button actionButton;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productDescriptionTextView = itemView.findViewById(R.id.productDescriptionTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productImageView = itemView.findViewById(R.id.productImageView);
            actionButton = itemView.findViewById(R.id.actionButton);
        }

        public void bind(final Product product) {
            productNameTextView.setText(product.getName());
            productDescriptionTextView.setText(product.getDescription());
            productPriceTextView.setText(String.format("%.2f RON", product.getPrice()));
            productImageView.setImageResource(product.getImageResourceId());

            // Ajustează textul și acțiunile butonului în funcție de starea produsului
            if (ShoppingCart.isInCart(product)) {
                actionButton.setText("Elimină din coș");
                actionButton.setOnClickListener(v -> onRemoveFromCartClickListener.onRemoveFromCartClicked(product));
            } else {
                actionButton.setText("Adaugă în coș");
                actionButton.setOnClickListener(v -> onAddToCartClickListener.onAddToCartClicked(product));
            }
        }
    }
}
