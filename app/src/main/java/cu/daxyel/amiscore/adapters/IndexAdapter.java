package cu.daxyel.amiscore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import cu.daxyel.amiscore.R;
import cu.daxyel.amiscore.models.Category;
import cu.daxyel.amiscore.models.Index;
import java.util.ArrayList;

public class IndexAdapter extends BaseExpandableListAdapter {
    
    private ArrayList<Category> categories;
    private Context context;

    public IndexAdapter(ArrayList<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }
    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public int getChildrenCount(int p1) {
        return categories.get(p1).getIndexes().size();
    }

    @Override
    public Object getGroup(int p1) {
        return categories.get(p1);
    }

    @Override
    public Object getChild(int p1, int p2) {
        return categories.get(p1).getIndexes().get(p2);
    }

    @Override
    public long getGroupId(int p1) {
        return p1;
    }

    @Override
    public long getChildId(int p1, int p2) {
        return p2;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int p1, boolean p2, View p3, ViewGroup p4) {
        Category category=(Category) getGroup(p1);
        if (p3 == null)
            p3 = LayoutInflater.from(context).inflate(R.layout.category_entry, null);
        TextView nameTv=p3.findViewById(R.id.category_name_tv);
        nameTv.setText(category.getName());
        return p3;
    }

    @Override
    public View getChildView(int p1, int p2, boolean p3, View p4, ViewGroup p5) {
        Index index=(Index) getChild(p1, p2);
        if (p4 == null)
            p4 = LayoutInflater.from(context).inflate(R.layout.index_entry, null);

        TextView nameTv=p4.findViewById(R.id.index_name_tv);
        nameTv.setText(index.getName());
        return p4;
    }

    @Override
    public boolean isChildSelectable(int p1, int p2) {
        return true;
    }


}
