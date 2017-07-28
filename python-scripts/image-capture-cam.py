import cv2
import time

camera_port = 0
ramp_frames = 30

# Now we can initialize the camera capture object with the cv2.VideoCapture class.
# All it needs is the index to a camera port.
camera = cv2.VideoCapture(camera_port)

# Captures a single image from the camera and returns it in PIL format
def get_image():
    # read is the easiest way to get a full image out of a VideoCapture object.
    retval, im = camera.read()
    return im

while True:
    for i in xrange(ramp_frames):
        temp = get_image()
    print("Taking image...")
    # Take the actual image we want to keep
    camera_capture = get_image()
    file = "/home/pi/Documents/intel_ui/intel_app_ui/public/dist/img/pothole/pothole1.jpg"
    cv2.imwrite(file, camera_capture)

    time.sleep(1)

del (camera)