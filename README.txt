Group 2 Point of Sale System:
Ryan Helgeson: Register and Sale Classes
Bryan Deherty: Reporting class for Inventory and Sales.
Brent Gallegher: Inventory Class
Jacob Astar: Authentication class and GUI Screens.

To log into the point of sale system, use the following credentials(username and password is case sensitive):
RegisterID: 1
User: snaqvi
Password JavaRocks1

Your default password can be changed by visiting the file menu in the top left corner and selecting change password. Also in
that menu is a log out button which will return you to the login screen where you can enter a different register number to
access another register. Clicking Exit will create a confirmation prompt and subsequently quit the program.

To start a new sale, click new sale in the top right corner of the home screen. Items are added to the sale by entering the item ID
and desired quantity in the respected fields and clicking "Add Item". To remove a specific item from the sale, click the row of the item
in the table and then click the Remove Item button. Clicking Remove Item without selecting a row will remove the top item in the sale.

To see a list of available items, visit the Inventory Management screen by selecting the Inventory button
in the bottom left corner of the screen. This screen also allows you to make changes to inventory information, add new
items to the inventory and export several reports about the inventory.

Next to the inventory button is the User Management button. This screen is only accessible to managers and allows them to
change user information, reset user passwords(the reset default password is: default1), disable user access, and create new users.
Users with a status of 'true' can log in to any register in the system.

Returns can be done by clicking the Returns button on the bottom of the home screen. To load a transaction, click Load Transaction and
enter the register and saleID(Found on the receipt, or terminal for testing), separated by a comma. Returns can be performed from any register,
regardless of what register the initial sale was on. The left text area displays the sale as it is stored in the system and the
right text areas details actions that were taken in the transaction. You must select terminate transaction to end the sale.

To print sales reports, visit the Sales Report screen and enter the desired information. Leaving the default selections will export all
sales stored in the system. To export a specific date, enter the date in the format of "MM-DD-YYYY". Hold Shift while selecting
users to export the sales of multiple specific users.