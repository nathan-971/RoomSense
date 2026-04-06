import mysql.connector
from mysql.connector import Error

class Connection:
    def __init__(self):
        self.connection = None

    def Open(self):
        try:
            self.connection = mysql.connector.connect(
                host = '127.0.0.1',
                user = 'springuser',
                password = 'password1234',
                database = 'roomsense'
            )

            if self.connection.is_connected():
                print("Database Connection Created")
        
        except Error as ex:
            print(f"Error Occurred while Connecting to Database: {ex}")
            self.connection = None

    def Close(self):
        if self.connection and self.connection.is_connected():
            self.connection.close()
            print("Database Connection Closed")

    def Query(self, query, params, fetch = False):
        if(self.connection is None):
            raise Exception("Connection not opened")

        cursor = self.connection.cursor(dictionary = True)
        
        try:
            cursor.execute(query, params)

            if fetch:
                result = cursor.fetchall()
            else:
                self.connection.commit()
                result = None
            return result

        except Error as ex:
            print(f"Error Occurred when processing Query: {ex}")
            return None
        
        finally:
            cursor.close()