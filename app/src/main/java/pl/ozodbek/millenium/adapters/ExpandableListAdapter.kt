package pl.ozodbek.millenium.adapters

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseExpandableListAdapter
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import pl.ozodbek.millenium.R
import pl.ozodbek.millenium.data.Category
import pl.ozodbek.millenium.databinding.ListItemCategoryBinding
import pl.ozodbek.millenium.databinding.ListItemChildBinding

class ExpandableListAdapter(
    private val context: Context,
    private val categories: List<Category>
) : BaseExpandableListAdapter() {
    var expandedGroupPosition = -1

    override fun getGroupCount(): Int {
        return categories.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return categories[groupPosition].items.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return categories[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return categories[groupPosition].items[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val binding =
            ListItemCategoryBinding.inflate(LayoutInflater.from(parent?.context), parent, false)

        val category = getGroup(groupPosition) as Category
        binding.categoryName.text = category.name

        if (isExpanded) {
            binding.arrowIndicator.setImageResource(R.drawable.ic_collapse)
            val color = ContextCompat.getColor(context, R.color.default_pink)
            DrawableCompat.setTint(binding.arrowIndicator.drawable, color)
            binding.categoryName.setTextColor(ContextCompat.getColor(context, R.color.default_pink))
            binding.selectedView.visibility = View.VISIBLE
            binding.categoryLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.more_darker
                )
            )

            if (groupPosition == expandedGroupPosition) {
                val rotateAnimator =
                    ObjectAnimator.ofFloat(binding.arrowIndicator, "rotation", 0f, 180f)
                rotateAnimator.duration = 200
                rotateAnimator.start()
            }
        } else {
            binding.arrowIndicator.setImageResource(R.drawable.ic_expand)
            val color = ContextCompat.getColor(context, R.color.white)
            DrawableCompat.setTint(binding.arrowIndicator.drawable, color)
            binding.categoryName.setTextColor(ContextCompat.getColor(context, R.color.white))
            binding.categoryLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.more_dark2
                )
            )

            binding.selectedView.visibility = View.INVISIBLE
            if (groupPosition == expandedGroupPosition) {
                val rotateAnimator =
                    ObjectAnimator.ofFloat(binding.arrowIndicator, "rotation", 0f, 180f)
                rotateAnimator.duration = 200
                rotateAnimator.start()

            } else {
                binding.arrowIndicator.rotation = 180f
            }
        }


        return binding.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val _binding =
            ListItemChildBinding.inflate(LayoutInflater.from(parent?.context), parent, false)

        val item = getChild(groupPosition, childPosition) as String
        _binding.childName.text = item

        val expandAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_expand)
        val collapseAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_collapse)

        if (isLastChild) {
            collapseAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    _binding.childName.startAnimation(expandAnimation)
                }

                override fun onAnimationRepeat(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    _binding.childName.startAnimation(collapseAnimation)
                }
            })
        } else {
            expandAnimation.interpolator = AccelerateInterpolator()
            _binding.childName.startAnimation(expandAnimation)
        }


        return _binding.root
    }


    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
