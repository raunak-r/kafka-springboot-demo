import json
import java.io
from org.apache.commons.io import IOUtils
from java.nio.charset import StandardCharsets
from org.apache.nifi.processor.io import StreamCallback

class ModJSON(StreamCallback):
  def __init__(self):
        pass

  def process(self, inputStream, outputStream):
    text = IOUtils.toString(inputStream, StandardCharsets.UTF_8)    
    log.warn("PYTHONSCRIPT - text - {}".format(text))

    obj = json.loads(text)
    log.warn("PYTHONSCRIPT - obj - {}".format(obj))

    newObj = {
          "field1": obj['filename'],
          "field2": obj['field2.0']
        }
    log.warn("PYTHONSCRIPT - newObj - {}".format(newObj))

    outputStream.write(bytearray(json.dumps(newObj, indent=4).encode('utf-8')))

log.warn("PYTHONSCRIPT - adsfg")

try:
  flowFile = session.get()
  if (flowFile != None):
    log.warn("PYTHONSCRIPT - hgfjhg")
    flowFile = session.write(flowFile, ModJSON())
    flowFile = session.putAttribute(flowFile, "filename", flowFile.getAttribute('filename').split('.')[0]+'_translated.json')

  session.transfer(flowFile, REL_SUCCESS)
  log.warn("PYTHONSCRIPT - FINAL ")
  session.commit()
except Exception as e:
  log.warn("EXECEPTION OCCURRED-{}".format(e))


"""
python blog - https://community.cloudera.com/t5/Community-Articles/Python-Script-in-NiFi/ta-p/246406

Groovy blog - https://funnifi.blogspot.com/2016/02/executescript-processor-hello-world.html
"""