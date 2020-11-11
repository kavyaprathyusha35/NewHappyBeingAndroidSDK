package com.nsmiles.happybeingsdklib.network;


import com.nsmiles.happybeingsdklib.Models.CorporateWellbeing.CorporateWellbeingReportModel;
import com.nsmiles.happybeingsdklib.Models.PaymentPackagesModel.PaymentPackagesInfo;
import com.nsmiles.happybeingsdklib.Models.SendEmailModel;
import com.nsmiles.happybeingsdklib.Models.SendGratitudeModel;
import com.nsmiles.happybeingsdklib.Reports.pregnancywellbeing.pregnancywellbeingmodel.GeneralWellBeingModel;
import com.nsmiles.happybeingsdklib.ServerApiConnectors.MyJsonObject;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.CorporateSuccess;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.answermodel.AssessmentJsonModel;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.assessmentcompleted.AssessmentCompletedStatus;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.categorymodel.WellBeingCategoryStatusModel;
import com.nsmiles.happybeingsdklib.wellbeingassessment.model.questionmodel.CorporateAssessModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Service {
    private final NetworkService networkService;
    MyJsonObject jsonObject;
    String success;
    MyJsonObject successJsonObject;
    long daysLeftlong;
    int dayLeft = -1;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }


    public Subscription getWellBeingAllCompletedStatus(String token, String URL, final WellBeingAllCompeltedCallBack callBack) {

        return networkService.getWellBeingAllCompletedStatus(token, URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends AssessmentCompletedStatus>>() {
                    @Override
                    public Observable<? extends AssessmentCompletedStatus> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<AssessmentCompletedStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof retrofit2.adapter.rxjava.HttpException) {
                            ResponseBody responseBody = ((retrofit2.adapter.rxjava.HttpException) e).response().errorBody();
                            try {
                                if (responseBody != null) {
                                    callBack.onError(new NetworkError(e));
                                }
                            } catch (IllegalStateException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(AssessmentCompletedStatus assessmentCompletedStatus) {
                        if (assessmentCompletedStatus != null && assessmentCompletedStatus.getSuccess() != null) {
                            callBack.onSuccess(assessmentCompletedStatus);
                        }
                    }
                });
    }


    public interface WellBeingAllCompeltedCallBack {

        void onSuccess(AssessmentCompletedStatus assessmentCompletedStatus);

        void onError(NetworkError networkError);
    }


    public Subscription getNSmilesAssessmentQuestion(String API_URL, final GetNSmilesAssessmentQuestionCallBack callBack) {

        return networkService.getQuestionnaires(API_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends CorporateAssessModel>>() {
                    @Override
                    public Observable<? extends CorporateAssessModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })

                .subscribe(new Subscriber<CorporateAssessModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            try {
                                if (responseBody != null) {
                                    callBack.onError(new NetworkError(e));
                                }
                            } catch (IllegalStateException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(CorporateAssessModel corporateAssessmentModel) {
                        if (corporateAssessmentModel.getSuccess() != null) {
                            callBack.onSuccess(corporateAssessmentModel);
                        } else {
                            callBack.onSuccessError(corporateAssessmentModel.getErrors());
                        }
                    }
                });
    }

    public interface GetNSmilesAssessmentQuestionCallBack {

        void onSuccess(CorporateAssessModel corporateAssessmentModel);

        void onError(NetworkError networkError);

        void onSuccessError(String errorMessage);
    }
   /*Get  Assessment Question MultipleReportData*/



      /*Generate  Report Answer Passing Api*/

    public Subscription generateAssessmentReportApi(String token, AssessmentJsonModel assessmentData, final GenerateReportCallBack callBack) {

        return networkService.generateCorporateWellBeingAssessmentReport(token, assessmentData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends CorporateSuccess>>() {
                    @Override
                    public Observable<? extends CorporateSuccess> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<CorporateSuccess>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            try {
                                if (responseBody != null) {
                                    callBack.onError(new NetworkError(e));
                                }
                            } catch (IllegalStateException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }                    }

                    @Override
                    public void onNext(CorporateSuccess corporateSuccess) {
                        if (corporateSuccess.getSuccess() != null) {
                            callBack.onSuccess(corporateSuccess);
                        } else {
                            callBack.onSuccessError(corporateSuccess.getErrors());

                        }

                    }


                });
    }

    public interface GenerateReportCallBack {

        void onSuccess(CorporateSuccess corporateSuccess);

        void onError(NetworkError networkError);

        void onSuccessError(String errorMessage);
    }
   /*Generate Report Answer Passing Api*/


   /*General WellBeing Report*/

    public Subscription getGeneralWellBeingReport(String token, String userId, final GeneralWellBeingReportCallBack callBack) {

        return networkService.getGeneralWellBeingReport(token, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends GeneralWellBeingModel>>() {
                    @Override
                    public Observable<? extends GeneralWellBeingModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<GeneralWellBeingModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            try {
                                if (responseBody != null) {
                                    callBack.onError(new NetworkError(e));
                                }
                            } catch (IllegalStateException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }                    }

                    @Override
                    public void onNext(GeneralWellBeingModel generalWellBeingModel) {
                        if (generalWellBeingModel.getSuccess() != null) {
                            callBack.onSuccess(generalWellBeingModel);
                        } else {
                            callBack.onSuccessError(generalWellBeingModel.getErrors());
                        }
                    }
                });
    }

    public interface GeneralWellBeingReportCallBack {

        void onSuccess(GeneralWellBeingModel generalWellBeingModel);

        void onError(NetworkError networkError);

        void onSuccessError(String errorMessage);
    }

   /*General WellBeing Report*/



   /*General WEllBeing WellBeingCategoryCategory Report*/

    public Subscription getPaymentPackagesInfo(String token, String API_URL,
                                                          final PaymentPackagesCallBack callBack) {

        return networkService.getPaymentPackages(token,
                API_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends PaymentPackagesInfo>>() {
                    @Override
                    public Observable<? extends PaymentPackagesInfo> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<PaymentPackagesInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            try {
                                if (responseBody != null) {
                                    callBack.onError(new NetworkError(e));
                                }
                            } catch (IllegalStateException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(PaymentPackagesInfo generalWellBeingModel) {
                        if (generalWellBeingModel.getSuccess() != null) {
                            callBack.onSuccess(generalWellBeingModel);
                        } else {
                            callBack.onSuccessError(generalWellBeingModel.getErrors());
                        }
                    }
                });
    }

    public Subscription getCorporateWellBeingCategoryReport(String token,
                                                            String API_URL,
                                                            String reportName,
                                                            List<String> category,
                                                            final CorporateWellbeingReportCallBack callBack) {

        return networkService.getCorporateWellBeingCategoryReport(token,
                API_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends CorporateWellbeingReportModel>>() {
                    @Override
                    public Observable<? extends CorporateWellbeingReportModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<CorporateWellbeingReportModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            try {
                                if (responseBody != null) {
                                    callBack.onError(new NetworkError(e));
                                }
                            } catch (IllegalStateException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(CorporateWellbeingReportModel generalWellBeingModel) {
                        if (generalWellBeingModel.getSuccess() != null) {
                            callBack.onSuccess(generalWellBeingModel);
                        } else {
                            callBack.onSuccessError("###Error###");
                        }
                    }
                });
    }


    public interface  PaymentPackagesCallBack{

        void onSuccess(PaymentPackagesInfo paymentPackagesInfo);

        void onError(NetworkError networkError);

        void onSuccessError(String errorMessage);
    }

    public interface CorporateWellbeingReportCallBack {

        void onSuccess(CorporateWellbeingReportModel corporateWellBeingModel);

        void onError(NetworkError networkError);

        void onSuccessError(String errorMessage);
    }

   /*General WEllBeing WellBeingCategoryCategory Report*/




   /*Check Payment Status*/


/*
    public Subscription paidStatusCheck(String token, HBUser loginInfo, final PaidStatusCallBack paidStatusCallBack) {

        return networkService.paidStatusApi(token, loginInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .onErrorResumeNext(new Func1<Throwable, Observable<? extends PaidStatus>>() {
                    @Override
                    public Observable<? extends PaidStatus> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<PaidStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            try {
                                if (responseBody != null) {
                                    paidStatusCallBack.onError(new NetworkError(e));
                                }
                            } catch (IllegalStateException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }                    }

                    @Override
                    public void onNext(PaidStatus success) {

                        if (success.getSuccess() != null) {
                            paidStatusCallBack.onSuccess(success);
                        } else {
                            paidStatusCallBack.onSuccessError(success.getError());
                        }

                    }

                });
    }
*/

/*
    public interface PaidStatusCallBack {

        void onSuccess(PaidStatus paidStatus);

        void onSuccessError(String errorMessage);

        void onError(NetworkError networkError);
    }
*/
   /*Check Payment Status*/


   /*Push Alert Notification and Gender MultipleReportData */


   /*Push Alert Notification and Gender MultipleReportData */



   /*Job Employability Report
   *
   *
   *
   * */
         /*Achieve Exam Status ---  > Study Planner Status*/


         /*Achieve Exam Status ---  > Concentration Status*/



     /*Achieve Exam Status ---  > Concentration Status*/



     /*Achieve Exam Status ---  > Will Power*/



     /*Achieve Exam Status ---  > Will Power*/






     /*Achieve Exam Multiple intellegince report */



     /*Study Planner Report*/


     /*Study Planner Report*/

     /*WellBeing Category Status Check Api*/


/*
    public Subscription getWellBeingCategoryStatus(String token, String URL, final WellBeingCategoryStatusCheckCallBack callBack) {

        return networkService.getWellBeingCategoryStatus(token, URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends WellBeingCategoryStatusModel>>() {
                    @Override
                    public Observable<? extends WellBeingCategoryStatusModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<WellBeingCategoryStatusModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            try {
                                if (responseBody != null) {
                                    callBack.onError(new NetworkError(e));
                                }
                            } catch (IllegalStateException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(WellBeingCategoryStatusModel wellBeingCategoryStatusModel) {
                        if (wellBeingCategoryStatusModel != null && wellBeingCategoryStatusModel.getSuccess() != null) {
                            callBack.onSuccess(wellBeingCategoryStatusModel);

                        }
                    }
                });

    }
*/

/*
    public interface WellBeingCategoryStatusCheckCallBack {

        void onSuccess(WellBeingCategoryStatusModel wellBeingCategoryStatusModel);

        void onError(NetworkError networkError);

    }
*/
     /*WellBeing Category Status Check Api*/


     /*Check All Assessment completed status*/

/*
    public Subscription getWellBeingAllCompletedStatus(String token, String URL, final WellBeingAllCompeltedCallBack callBack) {

        return networkService.getWellBeingAllCompletedStatus(token, URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends AssessmentCompletedStatus>>() {
                    @Override
                    public Observable<? extends AssessmentCompletedStatus> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<AssessmentCompletedStatus>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            try {
                                if (responseBody != null) {
                                    callBack.onError(new NetworkError(e));
                                }
                            } catch (IllegalStateException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(AssessmentCompletedStatus assessmentCompletedStatus) {
                        if (assessmentCompletedStatus != null && assessmentCompletedStatus.getSuccess() != null) {
                            callBack.onSuccess(assessmentCompletedStatus);
                        }
                    }
                });
    }
*/




    public interface OverComeExamPaymentStatusCallBack {
        void onSuccess(boolean overComeExamPaymentModel);
        void onError(NetworkError networkError);
        void onCompleted();
    }


//    private Func1<retrofit2.Response<ResponseBody>, Observable<File>> processResponse(final File dir, final String fileName, final CareerDownloadPDFInterface callBack) {
//        return new Func1<retrofit2.Response<ResponseBody>, Observable<File>>() {
//            @Override
//            public Observable<File> call(retrofit2.Response<ResponseBody> responseBodyResponse) {
//                return saveToDiskRx(responseBodyResponse,dir,fileName, callBack);
//            }
//        };
//    }


     /*
     *
     *
     *
     * */



     public Subscription SendGratitudeMailToFriend(SendGratitudeModel sendGratitudeModel, final SendGratitudeCallBack callBack){
        return networkService.gratitudeToOthers(sendGratitudeModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends SendEmailModel>>() {
                    @Override
                    public Observable<? extends SendEmailModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<SendEmailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SendEmailModel sendEmailModel) { if(sendEmailModel!=null){

                        if(sendEmailModel.getStatus()!=null) {
                            if (sendEmailModel.getStatus() == 1) {
                                callBack.onSuccess(sendEmailModel);
                            } else {
                                callBack.onError(NetworkError.DEFAULT_ERROR_MESSAGE);
                            }
                        }
                        else {
                            callBack.onError(sendEmailModel.getError());
                        }

                    }
                    else {
                        callBack.onError(NetworkError.DEFAULT_ERROR_MESSAGE);
                    }
                    }
                });


     }
    public interface SendGratitudeCallBack {
        void onSuccess(SendEmailModel sendEmailModel);
        void onError(String error);
        void onCatch();

    }

    public Subscription getWellBeingCategoryStatus(String token, String URL, final WellBeingCategoryStatusCheckCallBack callBack) {

        return networkService.getWellBeingCategoryStatus(token, URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends WellBeingCategoryStatusModel>>() {
                    @Override
                    public Observable<? extends WellBeingCategoryStatusModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<WellBeingCategoryStatusModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof retrofit2.adapter.rxjava.HttpException) {
                            ResponseBody responseBody = ((retrofit2.adapter.rxjava.HttpException) e).response().errorBody();
                            try {
                                if (responseBody != null) {
                                    callBack.onError(new NetworkError(e));
                                }
                            } catch (IllegalStateException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(WellBeingCategoryStatusModel wellBeingCategoryStatusModel) {
                        if (wellBeingCategoryStatusModel != null && wellBeingCategoryStatusModel.getSuccess() != null) {
                            callBack.onSuccess(wellBeingCategoryStatusModel);

                        }
                    }
                });

    }

    public interface WellBeingCategoryStatusCheckCallBack {

        void onSuccess(WellBeingCategoryStatusModel wellBeingCategoryStatusModel);

        void onError(NetworkError networkError);

    }

}



