import rsa
import random

safeStoragePhonePublic, safeStoragePhonePrivate = rsa.newkeys(512)
keyPhonePublic, keyPhonePrivate = rsa.newkeys(512)


#on safe storage side
randomNumber = random.randint(1000,9999)
message = str(randomNumber).encode('utf8')
crypto = rsa.encrypt(message, keyPhonePublic)

# -----> sending

#on key phone side
message = rsa.decrypt(crypto,keyPhonePrivate)
numberplusone = int(message) + 1
message = str(numberplusone).encode('utf8')
crypto = rsa.encrypt(message, safeStoragePhonePublic)

# <------ sending

#on safe storage side
message = rsa.decrypt(crypto, safeStoragePhonePrivate)
number = int(message)

if number == randomNumber + 1:
    print("Opening safe.")
else:
    print("Safe is still closed.")
