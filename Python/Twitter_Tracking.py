#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# Imports
import time
import threading
import tweepy
from tweepy import OAuthHandler

# Class to track twitter account's followers and friends' count.
class Track(threading.Thread):
    
    def __init__(self, user, delay):
        super(Track, self).__init__()
        self._stop = threading.Event()
        self._user = user
        self._delay = delay
    
    def stop(self):
        self._stop.set()
    
    def stopped(self):
        return self._stop.isSet()        
    
    def run(self):
        print("| local time | followers | friends |")
        while True:
            if self.stopped():
                break
            else:
                print("| " +
                      str(time.asctime(time.localtime(time.time()))) + " | " +
                      str(self._user.followers_count) + " | " +
                      str(self._user.friends_count) + " |")
                time.sleep(self._delay)

# Authentication
consumer_key = "ad4CZfcHHe3A5MDhs8f59waZf"
consumer_secret = "LI8jIrHxUCCQYf9CCqqsclgSqMgOQSkdS3UGUD4Xh3ZWnggeCE"
access_token = "2548312700-6SCdZAZzzNcZq1UGy60AVUoE5qzDnHchbP5tugO"
access_token_secret = "1Ik9fbCA0Ii1tOsrQSG9xyqyGtfLsOMwLFwWa23EijTxV"

auth = OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth)

# User
screen_name = "FateGO_USA"
user = api.get_user(screen_name=screen_name)

# Track interval - seconds
delay = 5

def main():
    try:
        print()
        print("Tracking starting ...")
        print("Press <enter> to stop.")
        print()
        track = Track(user, delay)
        track.start()
        input()
        track.stop()
        print("Tracking ended.")
    except:
        print("Exception caught ...")

if __name__ == "__main__":
    main()