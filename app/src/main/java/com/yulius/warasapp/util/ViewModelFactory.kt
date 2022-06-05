package com.yulius.warasapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yulius.warasapp.data.model.UserPreference
import com.yulius.warasapp.ui.articles.ArticlesViewModel
import com.yulius.warasapp.ui.auth.check_email.CheckEmailViewModel
import com.yulius.warasapp.ui.auth.login.LoginViewModel
import com.yulius.warasapp.ui.auth.register.RegisterViewModel
import com.yulius.warasapp.ui.auth.register2.RegisterViewModel2
import com.yulius.warasapp.ui.auth.reset_password.ResetPasswordViewModel
import com.yulius.warasapp.ui.auth.verification_data.VerificationDataViewModel
import com.yulius.warasapp.ui.diagnose.DiagnoseViewModel
import com.yulius.warasapp.ui.diagnose.diagnose1.Diagnose1ViewModel
import com.yulius.warasapp.ui.diagnose.diagnose2.Diagnose2ViewModel
import com.yulius.warasapp.ui.diagnose.diagnose3.Diagnose3ViewModel
import com.yulius.warasapp.ui.diagnose.diagnose4.Diagnose4ViewModel
import com.yulius.warasapp.ui.diagnose.diagnose5.Diagnose5ViewModel
import com.yulius.warasapp.ui.diagnose.diagnose6.Diagnose6ViewModel
import com.yulius.warasapp.ui.diagnose.diagnose7.Diagnose7ViewModel
import com.yulius.warasapp.ui.diagnose.recommendation.RecommendationViewModel
import com.yulius.warasapp.ui.diagnose.result.ResultDiagnoseViewModel
import com.yulius.warasapp.ui.home.HomeViewModel
import com.yulius.warasapp.ui.hospital.MapsViewModel
import com.yulius.warasapp.ui.landing.LandingViewModel
import com.yulius.warasapp.ui.main.MainViewModel
import com.yulius.warasapp.ui.profile.ProfileViewModel
import com.yulius.warasapp.ui.profile.change_password.ChangePasswordViewModel
import com.yulius.warasapp.ui.profile.contact_us.ContactUsViewModel
import com.yulius.warasapp.ui.profile.editprofile.EditProfileViewModel
import com.yulius.warasapp.ui.profile.feedback.FeedbackViewModel
import com.yulius.warasapp.ui.profile.history.HistoryViewModel
import com.yulius.warasapp.ui.profile.history.daily_report.DailyReportViewModel
import com.yulius.warasapp.ui.profile.history.detail_history.DetailHistoryViewModel
import com.yulius.warasapp.ui.profile.history.report.ReportViewModel
import com.yulius.warasapp.ui.profile.setting.ThemeViewModel
import com.yulius.warasapp.ui.splash_screen.SplashScreenViewModel

class ViewModelFactory(private val pref: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel(pref) as T
            }

            modelClass.isAssignableFrom(LandingViewModel::class.java) -> {
                LandingViewModel(pref) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }

            modelClass.isAssignableFrom(ResultDiagnoseViewModel::class.java) -> {
                ResultDiagnoseViewModel(pref) as T
            }

            modelClass.isAssignableFrom(DailyReportViewModel::class.java) -> {
                DailyReportViewModel(pref) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(pref) as T
            }

            modelClass.isAssignableFrom(CheckEmailViewModel::class.java) -> {
                CheckEmailViewModel() as T
            }

            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(pref) as T
            }

            modelClass.isAssignableFrom(VerificationDataViewModel::class.java) -> {
                VerificationDataViewModel() as T
            }

            modelClass.isAssignableFrom(ResetPasswordViewModel::class.java) -> {
                ResetPasswordViewModel() as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref) as T
            }

            modelClass.isAssignableFrom(DiagnoseViewModel::class.java) -> {
                DiagnoseViewModel(pref) as T
            }

            modelClass.isAssignableFrom(Diagnose1ViewModel::class.java) -> {
                Diagnose1ViewModel(pref) as T
            }

            modelClass.isAssignableFrom(Diagnose2ViewModel::class.java) -> {
                Diagnose2ViewModel(pref) as T
            }

            modelClass.isAssignableFrom(Diagnose3ViewModel::class.java) -> {
                Diagnose3ViewModel(pref) as T
            }

            modelClass.isAssignableFrom(Diagnose4ViewModel::class.java) -> {
                Diagnose4ViewModel(pref) as T
            }

            modelClass.isAssignableFrom(Diagnose5ViewModel::class.java) -> {
                Diagnose5ViewModel(pref) as T
            }

            modelClass.isAssignableFrom(Diagnose6ViewModel::class.java) -> {
                Diagnose6ViewModel(pref) as T
            }

            modelClass.isAssignableFrom(Diagnose7ViewModel::class.java) -> {
                Diagnose7ViewModel(pref) as T
            }

            modelClass.isAssignableFrom(RecommendationViewModel::class.java) -> {
                RecommendationViewModel(pref) as T
            }

            modelClass.isAssignableFrom(ReportViewModel::class.java) -> {
                ReportViewModel(pref) as T
            }

            modelClass.isAssignableFrom(ArticlesViewModel::class.java) -> {
                ArticlesViewModel() as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }

            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(pref) as T
            }

            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(pref) as T
            }

            modelClass.isAssignableFrom(DetailHistoryViewModel::class.java) -> {
                DetailHistoryViewModel(pref) as T
            }

            modelClass.isAssignableFrom(ContactUsViewModel::class.java) -> {
                ContactUsViewModel(pref) as T
            }

            modelClass.isAssignableFrom(FeedbackViewModel::class.java) -> {
                FeedbackViewModel(pref) as T
            }

            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> {
                ChangePasswordViewModel(pref) as T
            }

            modelClass.isAssignableFrom(ThemeViewModel::class.java) -> {
                ThemeViewModel(pref) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel2::class.java) -> {
                RegisterViewModel2(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }


    }