package Entities;

public enum MessageType {
	// server messages
    login, logout, disconnected, connected, Already_Logged_In,
	Login_Wrong_Input, Add_product_succ, Add_product_fail, Customer_list, Customer_list_update_Succ,
	Customer_registration_approved, Customer_registration_not_approved, Show_products_succ, download_report_pdf_succ,
	get_recipt, Orders_list, order_approved, showUsersToRegister, showUserDetails, insertCreditCardAndRegion,
	updateProductStock, addOrder, addDelivert, showRegistrationRequests, importUsersToRegistrate, registrateClubMember,
	updatePayment, updatePickupStatus, importWorkersToRegister, changeClubMemberStatus,insertIntoUsers,

	// client messages
	Show_all_monthly_orders_for_subscriber, Show_orders_history, Show_vending_machines, Add_product, Delete_Account,
	Check_account_existance, Show_products, Delete_product, Product_delete_succ, Get_orders_report, Order_delivered,
	Download_PDF_Report, Change_status_to_delivered_Succ, Order_Done, Show_Histogram, Get_reports, Show_report, getCard, getPickupOrder,
	showNewClubMebers,

	// deliver
	GetDeliveryOrder, notAcceptDelivery, AcceptDelivery, doneDelivery, notDoneDelivery, getUserToDelivery,getUserToDeliveryAndChangeAccept,

	// deliveryTimer
	setToDone,

	//marketing manager
	getPromtion, getRegion, setToActivate, setToDeActivate,
	
	// vending machines
	Get_vendingMachines, update_thresholdLevel, update_restockStatusToLowStatus, update_restockStatusToWaitToRestock,update_restockStatusToDone,

	// region manager
	Get_region, get_regionManagerByRegion, Get_workerMessages, insert_WorkerMessages, update_workerMessagesStatus, 

	//operation worker
	get_operationWorkerByRegion,
	
	// types of users
	Customer, ClubMember, CEO, RegionManager, MarketingWorker, CustomerServiceWorker,

	// Error
	Error;

}
