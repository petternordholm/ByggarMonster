#!/usr/bin/python
# -*- coding: UTF-8 -*-

### DEPENDENCIES #################
import os
import cgitb
import json
import cgi
import re
import sys
import base64
import commands

### CONFIG #######################
cgitb.enable()

### CONTROLLER ###################
input_base64 = base64.b64encode(base64.b64decode(cgi.FieldStorage().getvalue("input"))) #Validate
if input_base64 != None:
    print("Content-Type: application/json;charset=utf-8")
    print("")
    print("")
    jar = "../lib/target/byggarmonster-0.1-SNAPSHOT-jar-with-dependencies.jar"
    template = "../lib/src/resources/templates/simple.txt"
    command_string = "java -jar "+jar+" -source \"%s\" -output STDOUT -template %s" % (input_base64, template)
    #print(command_string)
    output = commands.getstatusoutput(command_string)[1]
    print(json.dumps({"input":input_base64, "output":output}))
    exit(0)

print("Content-Type: text/plain;charset=utf-8")
print("")
print("")
print "Error"
