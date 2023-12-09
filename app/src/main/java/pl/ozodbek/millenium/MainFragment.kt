package pl.ozodbek.millenium

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import pl.ozodbek.millenium.adapters.ExpandableListAdapter
import pl.ozodbek.millenium.data.Category
import pl.ozodbek.millenium.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)


        binding.navHeader.contactButton.setOnClickListener {
            findNavController().navigate(R.id.contactFragment)
        }

        val payment = Category(
            "Payment",
            listOf(
                "Transfers",
                "Phone top-ups",
                "Mobile contactless payments",
                "Beneficiaries",
                "Scan the code and pay",
                "BLIK payments",
                "Invoices and bills"
            )
        )
        val myFinance = Category(
            "My finance",
            listOf(
                "Summary",
                "Upcoming Payments",
                "Finance manager",
                "Accounts",
                "Cards",
                "Savings and investments",
                "Loans",
                "Insurance"
            )
        )
        val additionalServices = Category(
            "Additional Services",
            listOf(
                "Cashback",
                "Codes and Top-ups",
                "Transport tickets",
                "Parking meters",
                "Motorways",
                "Cinema tickets",
                "Loyalty cards",
                "Register SIM card"
            )
        )
        val offersForYou = Category(
            "Offers for you",
            listOf(
                "Accounts",
                "Savings and investments",
                "Like it? Share it!",
                "Insurance",
                "Motorgage loan",
                "Business opening"
            )
        )
        val milleAdministration = Category(
            "Mille administration",
            listOf(
                "500+ for Ukrainian citizen",
                "Family 500",
                "RKO Application",
                "Nursery co-financing",
                "Millennium ID",
                "Business opening"
            )
        )
        val information = Category(
            "Information",
            listOf("Find us", "Exchange rates", "What's new", "Discover the app", "Security")
        )

        val categories = listOf(
            payment,
            myFinance,
            additionalServices,
            offersForYou,
            milleAdministration,
            information
        )

        val adapter = ExpandableListAdapter(requireContext(), categories)
        binding.navHeader.expandableListView.setAdapter(adapter)

        binding.navHeader.expandableListView.setOnGroupExpandListener { groupPosition ->
            for (i in 0 until binding.navHeader.expandableListView.expandableListAdapter.groupCount) {
                if (i != groupPosition) {
                    binding.navHeader.expandableListView.collapseGroup(i)
                }
            }
            adapter.expandedGroupPosition = groupPosition
        }

        binding.navHeader.expandableListView.setOnGroupCollapseListener { groupPosition ->
            adapter.expandedGroupPosition = groupPosition
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

