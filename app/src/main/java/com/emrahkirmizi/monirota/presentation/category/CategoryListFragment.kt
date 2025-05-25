package com.emrahkirmizi.monirota.presentation.category

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.emrahkirmizi.monirota.R
import com.emrahkirmizi.monirota.databinding.FragmentCategoryListBinding
import com.emrahkirmizi.monirota.presentation.common.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryListFragment : Fragment(R.layout.fragment_category_list) {



    private val viewModel: CategoryViewModel by viewModels()
    private var _binding: FragmentCategoryListBinding? = null
    private val binding get() = _binding!!

    private val adapter = CategoryAdapter()

   // override fun onCreate(savedInstanceState: Bundle?) {
     //   super.onCreate(savedInstanceState)
    //    //setHasOptionsMenu(true)
   // }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCategoryListBinding.bind(view)

        binding.recyclerViewCategories.adapter = adapter



        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_category_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_add_category -> {
                        findNavController().navigate(R.id.action_categoryListFragment_to_addCategoryFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)




        //Swipe to Delete
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val category = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteCategoryById(category.id)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewCategories)

        //Listeyi gözlemle
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { list ->
                    adapter.submitList(list)
                    binding.textViewEmpty.visibility =
                        if (list.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    //override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    //    inflater.inflate(R.menu.menu_category_list, menu)
    //    super.onCreateOptionsMenu(menu, inflater)
    //}


   // override fun onOptionsItemSelected(item: MenuItem): Boolean {
       // return when (item.itemId) {
           // R.id.action_add_category -> {
           //     // Gidilecek yön
          //      findNavController().navigate(R.id.action_categoryListFragment_to_addCategoryFragment)
        //        true
       //     }
      //      else -> super.onOptionsItemSelected(item)
     //   }
    //}


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}
