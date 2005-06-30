package org.nakedobjects.viewer.skylark.example.border;
import org.nakedobjects.object.NakedObject;
import org.nakedobjects.viewer.skylark.Content;
import org.nakedobjects.viewer.skylark.Location;
import org.nakedobjects.viewer.skylark.RootObject;
import org.nakedobjects.viewer.skylark.Size;
import org.nakedobjects.viewer.skylark.View;
import org.nakedobjects.viewer.skylark.ViewAxis;
import org.nakedobjects.viewer.skylark.ViewSpecification;
import org.nakedobjects.viewer.skylark.Workspace;
import org.nakedobjects.viewer.skylark.basic.ObjectBorder;
import org.nakedobjects.viewer.skylark.example.ExampleViewSpecification;
import org.nakedobjects.viewer.skylark.example.TestObjectView;
import org.nakedobjects.viewer.skylark.example.TestViews;
import org.nakedobjects.viewer.skylark.metal.WindowBorder;
import org.nakedobjects.viewer.skylark.special.ScrollBorder;


public class CompoundBorderExample extends TestViews {

    public static void main(String[] args) {
        new CompoundBorderExample();
    }

    protected void views(Workspace workspace) {
        NakedObject object = createExampleObjectForView();
        Content content = new RootObject(object);
        ViewSpecification specification = new ExampleViewSpecification();        
        ViewAxis axis = null;
        
        View v = new TestObjectView(content, specification, axis, 300, 120, "normal");
        
        View objectBorder = new ObjectBorder(1, v);
        
        View scrollBorder = new ScrollBorder(objectBorder);
        scrollBorder.setSize(new Size(200, 200));
        
        View view = new WindowBorder(scrollBorder, false);
        view.setLocation(new Location(50, 60));
        view.setSize(view.getRequiredSize());
        workspace.addView(view);
        
        view = new WindowBorder(new TestObjectView(content, specification, axis, 100, 30, "active"), false);
        view.setLocation(new Location(200, 300));
        view.setSize(view.getRequiredSize());
        workspace.addView(view);
        
        view.getState().setActive();

    
        view = new WindowBorder(new TestObjectView(content, specification, axis, 100, 30, "view identified"), false);
        view.setLocation(new Location(200, 400));
        view.setSize(view.getRequiredSize());
        workspace.addView(view);
        
        view.getState().setInactive();
        view.getState().setRootViewIdentified();

    }
}


/*
Naked Objects - a framework that exposes behaviourally complete
business objects directly to the user.
Copyright (C) 2000 - 2005  Naked Objects Group Ltd

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

The authors can be contacted via www.nakedobjects.org (the
registered address of Naked Objects Group is Kingsway House, 123 Goldworth
Road, Woking GU21 1NR, UK).
*/