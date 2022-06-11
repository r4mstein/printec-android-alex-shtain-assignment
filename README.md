# printec-android-alex-shtain-assignment
<h3>Test task</h3>

We want an application that will provide the two basic transactions needed for a merchant:

<h4>Sales</h4>
In order to perform a Sales transaction, a merchant should be able a) to enter the transaction’s amount and b) to read the customer’s card.
The bank is then responsible to provide the transaction’s result (Approve or Reject).

<h4>Refund</h4>
Customers may sometimes return a previously purchased item, and they request a refund of the paid amount.
When they have paid by card, it is a complex transaction that needs to be performed: the receipt number and the amount
to be refunded must be sent to the bank and wait for an approval/rejection.

<h3>Application</h3>

<h4>Sales flow</h4>

This is the main functionality of the application. The user/merchant must enter the transaction’s amount, that
must be validated against the limits set in the settings (min/max allowed amount). If the amount is valid, an
embedded json file must be loaded, properly modified with the entered amount and finally sent
to server for approval. The server will respond with the transaction result (Approved/Declined) and with the
transaction data needed to format and print a real receipt. If the entered amount is not valid, surprise us!

<h4>Refund flow</h4>

In order to perform a Refund for a returned item, the user/merchant must first enter a valid receipt number
(validity verification will be performed through api call). The next step is to enter the amount to be refunded
(no validation needed) and perform the transaction. As in Sales transaction above, an embedded json file must
be loaded/modified and used in the communication with the server.

<h4>Settings</h4>

This option should allow the user to:
1) edit (increase/decrease) the transaction amount limits that it accepts (min/max amount) and
2) edit the server’s communication link (api url) that will be used for data verification (basically it will act as the Bank simulator)
