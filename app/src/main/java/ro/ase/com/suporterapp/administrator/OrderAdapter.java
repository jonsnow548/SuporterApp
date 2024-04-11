package ro.ase.com.suporterapp.administrator;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import ro.ase.com.suporterapp.R;
import ro.ase.com.suporterapp.shop.Order;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<Order> orderList;
    private Context context;
    private OrderStatusChangeListener statusChangeListener;

    public interface OrderStatusChangeListener {
        void onOrderStatusChanged();
    }

    public OrderAdapter(List<Order> orderList, Context context, OrderStatusChangeListener listener) {
        this.orderList = orderList;
        this.context = context;
        this.statusChangeListener = listener;
    }
    private void updateOrderStatus(Order order, String newStatus) {
        FirebaseFirestore.getInstance().collection("orders").document(order.getId())
                .update("status", newStatus)
                .addOnSuccessListener(aVoid -> {
                    Log.d("OrderAdapter", "Order status updated to " + newStatus);
                    if (statusChangeListener != null) {
                        statusChangeListener.onOrderStatusChanged();
                    }
                })
                .addOnFailureListener(e -> Log.e("OrderAdapter", "Error updating order status", e));
    }
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.administrator_order_item, parent, false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.addressTextView.setText(order.getAddress());
        holder.totalTextView.setText(String.format(context.getString(R.string.total_format), order.getTotal()));
        holder.paymentMethodTextView.setText(order.getPaymentMethod());
        holder.phoneNumberTextView.setText(order.getPhoneNumber());

        // Ajustează vizibilitatea butonului în funcție de statusul comenzii
        if (!"livrat".equals(order.getStatus())) {
            holder.manageOrderButton.setVisibility(View.VISIBLE);
            if ("nou".equals(order.getStatus())) {
                holder.manageOrderButton.setText(context.getString(R.string.mark_as_sent));
                holder.manageOrderButton.setOnClickListener(v -> updateOrderStatus(order, "trimis"));
            } else if ("trimis".equals(order.getStatus())) {
                holder.manageOrderButton.setText(context.getString(R.string.mark_as_delivered));
                holder.manageOrderButton.setOnClickListener(v -> updateOrderStatus(order, "livrat"));
            }
        } else {
            holder.manageOrderButton.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView addressTextView, totalTextView, paymentMethodTextView, phoneNumberTextView;
        Button manageOrderButton;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            totalTextView = itemView.findViewById(R.id.totalTextView);
            paymentMethodTextView = itemView.findViewById(R.id.paymentMethodTextView);
            phoneNumberTextView = itemView.findViewById(R.id.phoneNumberTextView);
            manageOrderButton = itemView.findViewById(R.id.manageOrderButton);
        }
    }
}
