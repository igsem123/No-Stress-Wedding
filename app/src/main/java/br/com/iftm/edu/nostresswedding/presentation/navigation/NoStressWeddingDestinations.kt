package br.com.iftm.edu.nostresswedding.presentation.navigation

sealed interface NoStressWeddingDestinations {
    val route: String

    object NoStressWeddingGraph : NoStressWeddingDestinations {
        override val route: String = "no_stress_wedding_graph"
    }

    object LoginScreen : NoStressWeddingDestinations {
        override val route: String = "login_screen"
    }

    object RegisterScreen : NoStressWeddingDestinations {
        override val route: String = "register_screen"
    }

    object HomeScreen : NoStressWeddingDestinations {
        override val route: String = "home_screen"
    }

    object GuestScreen : NoStressWeddingDestinations {
        override val route: String = "guest_screen"
    }

    object GuestFormScreen : NoStressWeddingDestinations {
        override val route: String = "guest_form_screen"
    }

    object VendorScreen : NoStressWeddingDestinations {
        override val route: String = "vendor_screen"
    }

    object VendorFormScreen : NoStressWeddingDestinations {
        override val route: String = "vendor_form_screen"
    }

    object WeddingDetailsScreen : NoStressWeddingDestinations {
        override val route: String = "wedding_details_screen"
    }

    object WeddingFormScreen : NoStressWeddingDestinations {
        override val route: String = "wedding_form_screen"
    }

    object GiftScreen : NoStressWeddingDestinations {
        override val route: String = "gift_screen"
    }

    object GiftFormScreen : NoStressWeddingDestinations {
        override val route: String = "gift_form_screen"
    }

    object PaymentScreen : NoStressWeddingDestinations {
        override val route: String = "payment_screen"
    }

    object PaymentFormScreen : NoStressWeddingDestinations {
        override val route: String = "payment_form_screen"
    }
}